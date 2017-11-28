package edu.cofc;

import javafx.application.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main extends Application {
    //UI Needs to have access to our Classes
    public static User user = new User();
    public static StepHistory stepHistory = new StepHistory();
    public static SleepHistory sleepHistory = new SleepHistory();
    public static HeartHistory heartHistory = new HeartHistory();
    public static Sync sync = new Sync(user);
    public static Timer timer = new Timer();
    public Scene scene;
    public Pane test;
    public Button btnTest;
    BorderPane border;

    public GridPane addSleepTracker() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(0, 5, 0, 5));

        // Category in column 2, row 1
        Label category = new Label();
        category.setWrapText(true);

        category.setFont(Font.font("Arial", FontWeight.BOLD, 10));

        grid.add(category, 0, 0, 3, 1);
        if(sleepHistory.sleepTracker.startSleepTracker()) {
            Runnable testRun = new Runnable() {
                public void run() {
                    try {
                        while (!sleepHistory.sleepTracker.isSleeping()) {
                            System.out.println("waiting to fall asleep");
                            category.setText("Waiting to fall asleep");
                            Thread.sleep(500);
                        }
                        while(sleepHistory.sleepTracker.isSleeping()) {
                            System.out.println("waiting to wake up");
                            category.setText("Waiting to wake up");
                            Thread.sleep(500);
                        }

                        category.setText("Saving sleep times");
                        sleepHistory.addSleepTimes(sleepHistory.sleepTracker.saveNightsSleep());

                        NightsSleep lastNight = sleepHistory.getSleepTimes(0);
                        System.out.printf("Fell Asleep At: %s\n", lastNight.getSleepTime());
                        System.out.printf("Woke Up At: %s\n", lastNight.getWakeTime());
                        System.out.printf("Seconds Asleep: %d\n", lastNight.secondsAsleep());

                        category.setText("Fell Asleep At: " + lastNight.getSleepTime() + "\nWoke Up At: " + lastNight.getWakeTime() + "\nSeconds Asleep: " + lastNight.secondsAsleep());
                    }
                    catch (InterruptedException e) {
                        //thread interrupted, display error
                    }
                }
            };

            Thread testThread = new Thread(testRun);
            testThread.start();
        }

        return grid;
    }

    public Pane addHeartMonitor() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(16, 16, 16, 32));

        // Category in column 2, row 1

        Button btn1 = new Button();
        Image imageDecline = new Image(getClass().getResourceAsStream("003-heartbeat.png"));
        ImageView imageView = new ImageView(imageDecline);
        imageView.setFitHeight(32);
        imageView.setFitWidth(32);
        btn1.setGraphic(imageView);

        btn1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                border.setCenter(addActiveHeartMonitor());

            }
        });
        GridPane.setHalignment(btn1, HPos.CENTER);
        GridPane.setValignment(btn1, VPos.CENTER);
        grid.add(btn1, 0, 1);


        return grid;
    }

    public GridPane addActiveHeartMonitor() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(0, 5, 0, 5));

        // Category in column 2, row 1
        Label category = new Label();
        category.setWrapText(true);

        category.setFont(Font.font("Arial", FontWeight.BOLD, 10));

        Button btnStop = new Button();
        Image imageStop = new Image(getClass().getResourceAsStream("002-stop.png"));
        ImageView imageViewStop = new ImageView(imageStop);
        imageViewStop.setFitHeight(16);
        imageViewStop.setFitWidth(16);
        btnStop.setGraphic(imageViewStop);

        btnStop.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                heartHistory.heartMonitor.stopHeart();
                border.setCenter(addStoppedHeartMonitor());
            }
        });

        Button btnReset = new Button();
        Image imageReset = new Image(getClass().getResourceAsStream("001-reload.png"));
        ImageView imageViewReset = new ImageView(imageReset);
        imageViewReset.setFitHeight(16);
        imageViewReset.setFitWidth(16);
        btnReset.setGraphic(imageViewReset);

        btnReset.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                heartHistory.heartMonitor.resetHeart();
            }
        });


        grid.add(category, 0, 0, 3, 1);
        grid.add(btnStop, 0, 2);
        grid.add(btnReset, 1, 2);

        if(heartHistory.heartMonitor.startHeart()) {
            Runnable testingThread = new Runnable() {
                public void run() {
                    try {
                        for (int i = 0; i < 40; i++) {
                            //Read for twenty seconds
                            System.out.printf("Heart Rate (BPM): %f\n", heartHistory.heartMonitor.readHeartRate());
                            category.setText("Heart Rate (BPM): " + heartHistory.heartMonitor.readHeartRate());
                            Thread.sleep(0);//500);
                        }
                    } catch (InterruptedException e) {
                        //thread interrupted display error
                    }
                }
            };

            //Thread testThread = new Thread(testingThread);
            //testThread.start();
            Platform.runLater(testingThread);
        }
        return grid;
    }

    public GridPane addStoppedHeartMonitor()
    {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(0, 5, 0, 5));

        // Category in column 2, row 1
        Button btnSave = new Button();
        Image imageSave = new Image(getClass().getResourceAsStream("003-save.png"));
        ImageView imageViewSave = new ImageView(imageSave);
        imageViewSave.setFitHeight(16);
        imageViewSave.setFitWidth(16);
        btnSave.setGraphic(imageViewSave);

        btnSave.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                heartHistory.addHeartRate(heartHistory.heartMonitor.saveHeartRate());
                heartHistory.heartMonitor.resetHeart();
            }
        });
        grid.add(btnSave, 2, 2);
        return grid;

}

    public GridPane addSyncData() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(0, 5, 0, 5));

        // Category in column 2, row 1
        Label category = new Label();
        category.setWrapText(true);

        category.setFont(Font.font("Arial", FontWeight.BOLD, 10));
        if(sync.addCompanion()) {
            Runnable syncRunnable = new Runnable() {
                public void run() {
                    try {
                        System.out.println("Starting the sync process");
                        category.setText("Starting the sync process");
                        sync.syncData("Empty Data to send");

                        Thread.sleep(2550);
                        System.out.println("Sync process completed");
                        category.setText("Sync process completed");

                        category.setText("Username: " + user.username + "\nGender: " + user.getGender() +
                                "\nBirthday: " + user.getBirthday() + "\nHeight: " + user.getHeight() + "\nWeight: " + user.getWeight());


                        System.out.println("User: " + user);
                        System.out.printf("Username: %s\n", user.username);
                        System.out.printf("Gender: %s\n", user.getGender());
                        System.out.printf("Birthday: %s\n", user.getBirthday());
                        System.out.printf("Height: %d\n", user.getHeight());
                        System.out.printf("Weight: %d\n", user.getWeight());
                    } catch (InterruptedException e) {
                        //thread interrupted, display sync error
                    }
                }
            };

            Thread syncThread = new Thread(syncRunnable);
            syncThread.start();
        }
        grid.add(category, 0, 0, 3, 1);


        return grid;
    }

    public GridPane addTimer() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(0, 5, 0, 5));

        // Category in column 2, row 1


        Label category = new Label("140");
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(300);
        slider.setValue(100);
        slider.setShowTickLabels(false);
        slider.setShowTickMarks(false);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);
        slider.setMaxWidth(100);
        slider.setMinWidth(100);
        slider.valueProperty().addListener((obs, oldval, newVal) ->
                slider.setValue(newVal.intValue()));
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {

                category.setText(String.format("%.0f", new_val));
            }
        });
        category.setWrapText(true);

        category.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Button btn1 = new Button();
        Image imageDecline = new Image(getClass().getResourceAsStream("002-play-button.png"));
        ImageView imageView = new ImageView(imageDecline);
        imageView.setFitHeight(16);
        imageView.setFitWidth(32);
        btn1.setGraphic(imageView);
        btn1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                timer.remainingTimeProperty().addListener((observable, oldValue, newValue) -> {
                    if(newValue.intValue() <= 0)
                    {
                        //completed
                    }
                    else
                    {
                        Platform.runLater(() -> category.setText(String.format("%d", newValue.intValue())));
                    }
                });
                timer.startTimer((int)slider.getValue());
            }
        });
        grid.add(slider, 0, 1);
        grid.add(btn1, 0, 2);

        grid.add(category, 0, 0, 3, 1);


        return grid;
    }

    public GridPane addStepCounter() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(16, 16, 16, 32));

        // Category in column 2, row 1

        Button btn1 = new Button();
        Image imageDecline = new Image(getClass().getResourceAsStream("002-play-button.png"));
        ImageView imageView = new ImageView(imageDecline);
        imageView.setFitHeight(32);
        imageView.setFitWidth(32);
        btn1.setGraphic(imageView);

        btn1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                border.setCenter(addActiveStepCounter());

            }
        });
        GridPane.setHalignment(btn1, HPos.CENTER);
        GridPane.setValignment(btn1, VPos.CENTER);
        grid.add(btn1, 0, 1);


        return grid;
    }

    public GridPane addActiveStepCounter() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(0, 5, 0, 5));

        // Category in column 2, row 1
        Label category = new Label();
        category.setWrapText(true);

        category.setFont(Font.font("Arial", FontWeight.BOLD, 10));

        Button btnStop = new Button();
        Image imageStop = new Image(getClass().getResourceAsStream("002-stop.png"));
        ImageView imageViewStop = new ImageView(imageStop);
        imageViewStop.setFitHeight(16);
        imageViewStop.setFitWidth(16);
        btnStop.setGraphic(imageViewStop);

        btnStop.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                stepHistory.stepCounter.stopCounter();
                border.setCenter(addStoppedStepCounter());
            }
        });

        Button btnReset = new Button();
        Image imageReset = new Image(getClass().getResourceAsStream("001-reload.png"));
        ImageView imageViewReset = new ImageView(imageReset);
        imageViewReset.setFitHeight(16);
        imageViewReset.setFitWidth(16);
        btnReset.setGraphic(imageViewReset);

        btnReset.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                stepHistory.stepCounter.resetSteps();
            }
        });


        grid.add(category, 0, 0, 3, 1);
        grid.add(btnStop, 0, 2);
        grid.add(btnReset, 1, 2);

        if(stepHistory.stepCounter.startCounter()) {
            Runnable testingThread = new Runnable() {
                public void run() {
                    try {
                        //This will be a while(onStepCountScreen) {} using sleep for testing purposes
                        //Update the text on screen
                        Thread.sleep(2000);
                        System.out.printf("Step Count: %d\n", stepHistory.stepCounter.readStepCount());
                        category.setText("Step Count: " + stepHistory.stepCounter.readStepCount());


                    } catch (InterruptedException e) {
                        //thread interrupted display error
                    }
                }
            };

            Platform.runLater(testingThread);
        }



        return grid;
    }

    public GridPane addStoppedStepCounter() {
        //just start button
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(0, 5, 0, 5));

        // Category in column 2, row 1
        Label category = new Label();
        category.setWrapText(true);

        category.setFont(Font.font("Arial", FontWeight.BOLD, 10));
        Button btn1 = new Button();
        Image imageDecline = new Image(getClass().getResourceAsStream("003-heartbeat.png"));
        ImageView imageView = new ImageView(imageDecline);
        imageView.setFitHeight(32);
        imageView.setFitWidth(32);
        btn1.setGraphic(imageView);

        btn1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
            }
        });

        Button btn2 = new Button();
        Image imageStop = new Image(getClass().getResourceAsStream("002-stop.png"));
        ImageView imageViewStop = new ImageView(imageStop);
        imageViewStop.setFitHeight(32);
        imageViewStop.setFitWidth(32);
        btn2.setGraphic(imageViewStop);

        btn2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                stepHistory.stepCounter.stopCounter();
            }
        });

        Button btnReset = new Button();
        Image imageReset = new Image(getClass().getResourceAsStream("001-reload.png"));
        ImageView imageViewReset = new ImageView(imageReset);
        imageViewReset.setFitHeight(32);
        imageViewReset.setFitWidth(32);
        btnReset.setGraphic(imageViewReset);

        btnReset.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                stepHistory.stepCounter.resetSteps();
            }
        });
        Button btnSave = new Button();
        Image imageSave = new Image(getClass().getResourceAsStream("003-heartbeat.png"));
        ImageView imageViewSave = new ImageView(imageSave);
        imageViewSave.setFitHeight(32);
        imageViewSave.setFitWidth(32);
        btnSave.setGraphic(imageViewSave);

        btnSave.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                stepHistory.addDailyCount(stepHistory.stepCounter.saveDailyCount());

            }
        });

        grid.add(category, 0, 0, 3, 1);
        grid.add(btn1, 0, 1);
        grid.add(btn2, 0, 2);
        grid.add(btnReset, 1, 2);
        grid.add(btnSave, 2, 2);
        return grid;
    }

    public GridPane addHomeScreen() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(0, 5, 0, 5));

        // Category in column 2, row 1
        Label category = new Label("10:00 AM");
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        category.setText(sdf.format(cal.getTime()));
        GridPane.setHalignment(category, HPos.CENTER);

        category.setFont(Font.font("Arial", FontWeight.BOLD, 10));
        category.setTextFill(Color.WHITE);

        //grid.add(category, 0, 0, 3, 1);

        Button btn1 = new Button();
        //btn1.setStyle("-fx-background-color: #e5e5e5;");
       // btn1.setText("T");
        Image imageTimer = new Image(getClass().getResourceAsStream("007-stopwatch.png"));
        ImageView imageViewTimer = new ImageView(imageTimer);
        imageViewTimer.setFitHeight(16);
        imageViewTimer.setFitWidth(16);
        btn1.setGraphic(imageViewTimer);
        btn1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                border.setCenter(addTimer());
                btnTest.setVisible(true);
            }
        });
        grid.add(btn1, 0, 1);

        Button btn2 = new Button();
        Image imageHeartMonitor = new Image(getClass().getResourceAsStream("003-heartbeat.png"));
        ImageView imageViewHeartMonitor = new ImageView(imageHeartMonitor);
        imageViewHeartMonitor.setFitHeight(16);
        imageViewHeartMonitor.setFitWidth(16);
        btn2.setGraphic(imageViewHeartMonitor);
        //btn2.setText("H");
        btn2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                border.setCenter(addHeartMonitor());
                btnTest.setVisible(true);
            }
        });
        grid.add(btn2, 2, 1);

        Button btn3 = new Button();
        //btn3.setText("ST");
        Image imageStepTracker = new Image(getClass().getResourceAsStream("004-stais.png"));
        ImageView imageViewStepTracker = new ImageView(imageStepTracker);
        imageViewStepTracker.setFitHeight(16);
        imageViewStepTracker.setFitWidth(16);
        btn3.setGraphic(imageViewStepTracker);
        btn3.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                border.setCenter(addStepCounter());
                btnTest.setVisible(true);
            }
        });
        grid.add(btn3, 1, 2);

        Button btn4 = new Button();
        //btn4.setText("Z");
        Image imageSleepTracker = new Image(getClass().getResourceAsStream("001-sleep.png"));
        ImageView imageViewSleepTracker = new ImageView(imageSleepTracker);
        imageViewSleepTracker.setFitHeight(16);
        imageViewSleepTracker.setFitWidth(16);
        btn4.setGraphic(imageViewSleepTracker);
        btn4.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                border.setCenter(addSleepTracker());
                btnTest.setVisible(true);
            }
        });
        grid.add(btn4, 0, 3);

        Button btn5 = new Button();
        //btn5.setText("SY");
        Image imageSync = new Image(getClass().getResourceAsStream("002-synchronization-arrows.png"));
        ImageView imageViewSync = new ImageView(imageSync);
        imageViewSync.setFitHeight(16);
        imageViewSync.setFitWidth(16);
        btn5.setGraphic(imageViewSync);
        btn5.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                border.setCenter(addSyncData());
                btnTest.setVisible(true);
            }
        });
        grid.add(btn5, 2, 3);

        BackgroundFill myBF = new BackgroundFill(Color.BLACK, new CornerRadii(1),
                new Insets(0.0,0.0,0.0,0.0));// or null for the padding
//then you set to your node or container or layout
        grid.setBackground(new Background(myBF));

        return grid;
    }
        @Override
        public void start(Stage primaryStage) throws Exception {


            //StackPane root = new StackPane();
            //root.getChildren().add(addHomeScreen());
            border = new BorderPane();
            HBox hbox = new HBox();
            btnTest = new Button();
            btnTest.setVisible(false);
            Image imageBack = new Image(getClass().getResourceAsStream("001-back.png"));
            ImageView imageViewBack = new ImageView(imageBack);
            imageViewBack.setFitHeight(12);
            imageViewBack.setFitWidth(12);
            btnTest.setGraphic(imageViewBack);
            btnTest.setStyle(
                            "-fx-min-width: 16px; " +
                            "-fx-min-height: 16px; " +
                            "-fx-max-width: 16px; " +
                            "-fx-max-height: 16px;"
            );
            btnTest.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    border.setCenter(addHomeScreen());
                    btnTest.setVisible(false);
                }
            });
            Label category = new Label("10:00 AM");
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
            category.setText(sdf.format(cal.getTime()));
            //category.setAlignment(Pos.TOP_RIGHT);
            //btnTest.setAlignment(Pos.TOP_LEFT);
            hbox.setPadding(new Insets(2,2,2,2));
            Region reg = new Region();
            reg.setPrefWidth(20);
            hbox.getChildren().add(btnTest);
            hbox.getChildren().add(reg);
            hbox.getChildren().add(category);

            border.setTop(hbox);
            border.setCenter(addHomeScreen());

            Pane mainBox = new Pane();
            Image watch = new Image(getClass().getResourceAsStream("watch.jpg"));
            BackgroundImage backgroundImage = new BackgroundImage(watch, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            mainBox.setBackground(new Background(backgroundImage));



            test = new Pane();
            BackgroundFill myBF = new BackgroundFill(Color.BLACK, new CornerRadii(0),
                    new Insets(0.0,0.0,0.0,0.0));// or null for the padding
//then you set to your node or container or layout
            test.setBackground(new Background(myBF));
            test.setMaxSize(113, 113);
            test.setMinSize(113, 113);
            test.setMaxSize(116, 116);
            test.setMinSize(116, 116);

            test.setLayoutX(17);
            test.setLayoutY(85);

            test.getChildren().add(border);
            mainBox.getChildren().add(test);
            scene = new Scene(mainBox, 150, 286);
            scene.getStylesheets().add
                    (getClass().getResource("button.css").toExternalForm());
            primaryStage.setTitle("Hello World!");
            primaryStage.setScene(scene);
            primaryStage.show();
        }

    public static void main(String args[]){
        launch(args);

         /* How the step counter works: (This will be activated by switching to step counter screen and pressing start)
            - Starts the step counter (constantly reading from the accelerometer)
            - Thread will be created to update the step count on screen
            - Stops the counter when the stop button is pressed
            - When saved is pressed it will save the daily step count
         */

        /*
        if(stepHistory.stepCounter.startCounter()) {
            Runnable testingThread = new Runnable() {
                public void run() {
                    try {
                        //This will be a while(onStepCountScreen) {} using sleep for testing purposes
                        //Update the text on screen
                        Thread.sleep(2000);
                        System.out.printf("Step Count: %d\n", stepHistory.stepCounter.readStepCount());

                    } catch (InterruptedException e) {
                        //thread interrupted display error
                    }

                    //Stop button pressed
                    stepHistory.stepCounter.stopCounter();

                    //Saves button pressed, saves and resets
                    stepHistory.addDailyCount(stepHistory.stepCounter.saveDailyCount());
                    stepHistory.stepCounter.resetSteps();
                }
            };

            Thread testThread = new Thread(testingThread);
            testThread.start();
        }
        */


        /*
           How the Timer works: (activated by switching to the timer screen and pressing start)
             - Inputs the desired seconds from the UI
             - Creates thread to count down
             - Stops when time has been reached
         */

        /*
        timer.startTimer(100); //1 minute 40 seconds for testing

         */

        /* Heart Monitor
            - Heart.startHeart will be called from pressing a button
            - A thread should be created to constantly be reading the Heart.getHeartRate()
            - Use this to update the UI
            - Heart.stopHeart and Heart.resetHeart will be used to stop the heart monitor from buttons
         */

        /*
        if(heartHistory.heartMonitor.startHeart()) {
            Runnable testingThread = new Runnable() {
                public void run() {
                    try {
                        for(int i = 0; i < 40; i++) {
                            //Read for twenty seconds
                            System.out.printf("Heart Rate (BPM): %f\n", heartHistory.heartMonitor.readHeartRate());

                            Thread.sleep(500);
                        }
                    } catch (InterruptedException e) {
                        //thread interrupted display error
                    }

                    //Stop button pressed
                    heartHistory.heartMonitor.stopHeart();

                    //Saves button pressed, saves and resets
                    heartHistory.addHeartRate(heartHistory.heartMonitor.saveHeartRate());
                    heartHistory.heartMonitor.resetHeart();
                }
            };

            Thread testThread = new Thread(testingThread);
            testThread.start();
        }
        */

        /* Sync, Companion and User
            - Sync should be called at the opening of the app
            - This will set up the user (just fills in values)
            - The sync process takes 2.5 seconds (just uses a sleep so this can change) so UI can show a 2.5 progress bar for the sync process?
         */

        /*
        if(sync.addCompanion()) {
            Runnable syncRunnable = new Runnable() {
                public void run() {
                    try {
                        System.out.println("Starting the sync process");
                        sync.syncData("Empty Data to send");

                        Thread.sleep(2550);
                        System.out.println("Sync process completed");

                        System.out.println("user: " + user);
                        System.out.printf("Username: %s\n", user.username);
                        System.out.printf("Gender: %s\n", user.getGender());
                        System.out.printf("Birthday: %s\n", user.getBirthday());
                        System.out.printf("Height: %d\n", user.getHeight());
                        System.out.printf("Weight: %d\n", user.getWeight());
                    } catch (InterruptedException e) {
                        //thread interrupted, display sync error
                    }
                }
            };

            Thread syncThread = new Thread(syncRunnable);
            syncThread.start();
        }
        */

        /* Sleep Tracker */

        /*
        if(sleepHistory.sleepTracker.startSleepTracker()) {
            Runnable testRun = new Runnable() {
                public void run() {
                    try {
                        while (!sleepHistory.sleepTracker.isSleeping()) {
                            System.out.println("waiting to fall asleep");
                            Thread.sleep(500);
                        }
                        while(sleepHistory.sleepTracker.isSleeping()) {
                            System.out.println("waiting to wake up");
                            Thread.sleep(500);
                        }

                        System.out.printf("Saving sleep times\n");
                        sleepHistory.addSleepTimes(sleepHistory.sleepTracker.saveNightsSleep());

                        NightsSleep lastNight = sleepHistory.getSleepTimes(0);
                        System.out.printf("Fell Asleep At: %s\n", lastNight.getSleepTime());
                        System.out.printf("Woke Up At: %s\n", lastNight.getWakeTime());
                        System.out.printf("Seconds Asleep: %d\n", lastNight.secondsAsleep());
                    }
                    catch (InterruptedException e) {
                        //thread interrupted, display error
                    }
                }
            };

            Thread testThread = new Thread(testRun);
            testThread.start();
        }
        */
    }
}
