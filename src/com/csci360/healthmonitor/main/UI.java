package com.csci360.healthmonitor.main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class UI extends Application {
    private UserSingleton userInstance = UserSingleton.getInstance();
    private StepHistorySingleton stepHistory = StepHistorySingleton.getInstance();
    private SleepHistorySingleton sleepHistory = SleepHistorySingleton.getInstance();
    private HeartHistorySingleton heartHistory = HeartHistorySingleton.getInstance();
    private SyncSingleton syncInstance = SyncSingleton.getInstance();
    private TimerSingleton timerInstance = TimerSingleton.getInstance();
    private Pane deviceScreenBackgroundPane;
    private Button backButton;
    private BorderPane mainBorderPane;


    @Override
    public void start(Stage primaryStage) throws Exception {
        mainBorderPane = new BorderPane();
        backButton = new Button();
        backButton.setVisible(false);

        Image imageBack = new Image(getClass().getResourceAsStream("resources/back.png"));
        ImageView imageViewBack = new ImageView(imageBack);
        imageViewBack.setFitHeight(12);
        imageViewBack.setFitWidth(12);
        backButton.setGraphic(imageViewBack);
        backButton.setStyle(
                "-fx-min-width: 16px; " +
                        "-fx-min-height: 16px; " +
                        "-fx-max-width: 16px; " +
                        "-fx-max-height: 16px;"
        );
        backButton.setOnAction(event -> {
                    mainBorderPane.setCenter(addHomeScreen());
                    backButton.setVisible(false);
                }
        );

        Label timeLabel = new Label("10:00 AM");
        CalendarObservable cal = new CalendarObservable();
        cal.currentTime.addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> timeLabel.setText(newValue));
        });
        timeLabel.setText(cal.currentTime.get());

        HBox statusBarBox = new HBox();
        statusBarBox.setPadding(new Insets(2, 2, 2, 2));
        Region spacerRegion = new Region();
        spacerRegion.setPrefWidth(20);
        statusBarBox.getChildren().add(backButton);
        statusBarBox.getChildren().add(spacerRegion);
        statusBarBox.getChildren().add(timeLabel);

        mainBorderPane.setTop(statusBarBox);
        mainBorderPane.setCenter(addHomeScreen());

        Pane mainBox = new Pane();
        Image watchBackground = new Image(getClass().getResourceAsStream("resources/watch.jpg"));
        BackgroundImage backgroundImage = new BackgroundImage(watchBackground, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        mainBox.setBackground(new Background(backgroundImage));


        deviceScreenBackgroundPane = new Pane();
        BackgroundFill screenBackground = new BackgroundFill(Color.BLACK, new CornerRadii(0), new Insets(0.0, 0.0, 0.0, 0.0));
        deviceScreenBackgroundPane.setBackground(new Background(screenBackground));
        deviceScreenBackgroundPane.setMaxSize(113, 113);
        deviceScreenBackgroundPane.setMinSize(113, 113);
        deviceScreenBackgroundPane.setMaxSize(116, 116);
        deviceScreenBackgroundPane.setMinSize(116, 116);
        deviceScreenBackgroundPane.setLayoutX(17);
        deviceScreenBackgroundPane.setLayoutY(85);
        deviceScreenBackgroundPane.getChildren().add(mainBorderPane);
        mainBox.getChildren().add(deviceScreenBackgroundPane);

        Scene mainScene = new Scene(mainBox, 150, 286);
        mainScene.getStylesheets().add(getClass().getResource("resources/style.css").toExternalForm());
        primaryStage.setTitle("FitBit");
        primaryStage.setScene(mainScene);
        primaryStage.setOnCloseRequest(event -> {
            stepHistory.stepCounter.stopCounter();
            sleepHistory.sleepTracker.stopSleepTracker();
            heartHistory.heartMonitor.stopHeart();
            timerInstance.stopTimer();
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();
    }

    public GridPane addSleepTracker() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(0, 5, 0, 5));
        Label mainLabel = new Label();
        mainLabel.setWrapText(true);
        mainLabel.setMaxWidth(113);
        mainLabel.setFont(Font.font("Arial", FontWeight.BOLD, 10));

        grid.add(mainLabel, 0, 0, 3, 1);
        if (sleepHistory.sleepTracker.startSleepTracker()) {
            Runnable sleepHistoryRunnable = () -> {
                try {
                    while (!sleepHistory.sleepTracker.isSleeping()) {
                        System.out.println("waiting to fall asleep");
                        Platform.runLater(() -> mainLabel.setText("Waiting to fall asleep"));
                        Thread.sleep(500);
                    }
                    while (sleepHistory.sleepTracker.isSleeping()) {
                        System.out.println("waiting to wake up");
                        Platform.runLater(() -> mainLabel.setText("Waiting to wake up"));
                        Thread.sleep(500);
                    }

                    Platform.runLater(() -> mainLabel.setText("Saving sleep times"));
                    sleepHistory.addSleepTimes(sleepHistory.sleepTracker.saveNightsSleep());

                    NightsSleep lastNight = sleepHistory.getSleepTimes(0);
                    System.out.printf("Fell Asleep At: %s\n", lastNight.getSleepTime());
                    System.out.printf("Woke Up At: %s\n", lastNight.getWakeTime());
                    System.out.printf("Seconds Asleep: %d\n", lastNight.secondsAsleep());

                    Platform.runLater(() -> mainLabel.setText("Fell Asleep At: " + lastNight.getSleepTime() + "\nWoke Up At: " + lastNight.getWakeTime() + "\nSeconds Asleep: " + lastNight.secondsAsleep()));
                } catch (InterruptedException e) {
                    //thread interrupted, display error
                }
            };

            Thread sleepHistoryThread = new Thread(sleepHistoryRunnable);
            sleepHistoryThread.start();
        }
        return grid;
    }

    public Pane addHeartMonitor() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(16, 16, 16, 32));
        Button startHeartMonitorButton = new Button();
        Image imageDecline = new Image(getClass().getResourceAsStream("resources/heartbeat.png"));
        ImageView imageView = new ImageView(imageDecline);
        imageView.setFitHeight(32);
        imageView.setFitWidth(32);
        startHeartMonitorButton.setGraphic(imageView);

        startHeartMonitorButton.setOnAction((actionEvent) -> { mainBorderPane.setCenter(addActiveHeartMonitor()); });
        GridPane.setHalignment(startHeartMonitorButton, HPos.CENTER);
        GridPane.setValignment(startHeartMonitorButton, VPos.CENTER);
        grid.add(startHeartMonitorButton, 0, 1);

        return grid;
    }

    public GridPane addActiveHeartMonitor() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(0, 5, 0, 5));

        Label heartRateLabel = new Label();
        heartRateLabel.setWrapText(true);
        heartRateLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        Button btnStop = new Button();
        Image imageStop = new Image(getClass().getResourceAsStream("resources/stop.png"));
        ImageView imageViewStop = new ImageView(imageStop);
        imageViewStop.setFitHeight(16);
        imageViewStop.setFitWidth(16);
        btnStop.setGraphic(imageViewStop);

        btnStop.setOnAction((actionEvent) -> {
            heartHistory.heartMonitor.stopHeart();
            mainBorderPane.setCenter(addStoppedHeartMonitor());
        });

        Button btnReset = new Button();
        Image imageReset = new Image(getClass().getResourceAsStream("resources/reload.png"));
        ImageView imageViewReset = new ImageView(imageReset);
        imageViewReset.setFitHeight(16);
        imageViewReset.setFitWidth(16);
        btnReset.setGraphic(imageViewReset);

        btnReset.setOnAction((actionEvent) -> {
            heartHistory.heartMonitor.resetHeart();
        });

        grid.add(heartRateLabel, 0, 0, 3, 1);
        grid.add(btnStop, 0, 2);
        grid.add(btnReset, 1, 2);

        if (heartHistory.heartMonitor.startHeart()) {
            heartHistory.heartMonitor.bpmProperty().addListener((observable, oldValue, newValue) -> {
                Platform.runLater(() -> heartRateLabel.setText(String.format("%.0f bpm", newValue)));
            });
        }

        return grid;
    }

    public GridPane addStoppedHeartMonitor() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(0, 5, 0, 5));

        Label heartRateLabel = new Label(String.format("%.0f bpm", heartHistory.heartMonitor.bpmProperty().get()));
        heartRateLabel.setWrapText(true);
        heartRateLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        heartRateLabel.setMaxWidth(113);
        heartRateLabel.setVisible(true);

        Button btnSave = new Button();
        Image imageSave = new Image(getClass().getResourceAsStream("resources/save.png"));
        ImageView imageViewSave = new ImageView(imageSave);
        imageViewSave.setFitHeight(16);
        imageViewSave.setFitWidth(16);
        btnSave.setGraphic(imageViewSave);

        btnSave.setOnAction((actionEvent) -> {
            heartHistory.addHeartRate(heartHistory.heartMonitor.saveHeartRate());
            heartHistory.heartMonitor.resetHeart();
            heartRateLabel.setText("Saved successfully");
        });
        grid.add(heartRateLabel, 0, 0);
        grid.add(btnSave, 0, 1);
        return grid;
    }

    public GridPane addSyncData() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(0, 5, 0, 5));

        Label syncStatusLabel = new Label();
        syncStatusLabel.setWrapText(true);
        syncStatusLabel.setMaxWidth(113);
        syncStatusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 10));
        if (syncInstance.addCompanion()) {
            Runnable syncRunnable = () -> {
                try {
                    System.out.println("Starting the sync process");
                    Platform.runLater(() -> syncStatusLabel.setText("Starting the sync process"));
                    syncInstance.syncData("Empty Data to send");

                    Thread.sleep(2550);
                    System.out.println("Sync process completed");
                    Platform.runLater(() -> syncStatusLabel.setText("Sync process completed"));

                    Platform.runLater(() -> syncStatusLabel.setText("Username: " + userInstance.username + "\nGender: " + userInstance.getGender() +
                            "\nBirthday: " + userInstance.getBirthday() + "\nHeight: " + userInstance.getHeight() + "\nWeight: " + userInstance.getWeight()));


                    System.out.println("UserSingleton: " + userInstance);
                    System.out.printf("Username: %s\n", userInstance.username);
                    System.out.printf("Gender: %s\n", userInstance.getGender());
                    System.out.printf("Birthday: %s\n", userInstance.getBirthday());
                    System.out.printf("Height: %d\n", userInstance.getHeight());
                    System.out.printf("Weight: %d\n", userInstance.getWeight());
                } catch (InterruptedException e) {
                    //thread interrupted, display syncInstance error
                }
            };

            Thread syncThread = new Thread(syncRunnable);
            syncThread.start();
        }
        grid.add(syncStatusLabel, 0, 0);
        return grid;
    }

    public GridPane addTimer() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(0, 5, 0, 5));
        Button btnStop = new Button();
        Label timerLabel = new Label("100 secs");
        Slider slider = new Slider();
        slider.setMin(1);
        slider.setMax(300);
        slider.setValue(100);
        slider.setShowTickLabels(false);
        slider.setShowTickMarks(false);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);
        slider.setMaxWidth(100);
        slider.setMinWidth(100);
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            slider.setValue(newValue.intValue());
            timerLabel.setText(String.format("%.0f secs", newValue));
        });
        timerLabel.setWrapText(true);

        timerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        Button startTimerButton = new Button();
        Image imageStartTimer = new Image(getClass().getResourceAsStream("resources/play-button.png"));
        ImageView imageView = new ImageView(imageStartTimer);
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        startTimerButton.setGraphic(imageView);
        timerInstance.remainingTimeProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() <= 0) {
                Platform.runLater(() -> {
                    mainBorderPane.setCenter(addFinishedTimer());
                    btnStop.setVisible(false);
                    slider.setVisible(true);
                });
            } else {
                Platform.runLater(() -> timerLabel.setText(String.format("%d secs", newValue.intValue())));
            }
        });
        startTimerButton.setOnAction(event -> {
                    timerInstance.startTimer((int) slider.getValue());
                    slider.setVisible(false);
                    btnStop.setVisible(true);
                }
        );


        Image imageStop = new Image(getClass().getResourceAsStream("resources/stop.png"));
        ImageView imageViewStop = new ImageView(imageStop);
        imageViewStop.setFitHeight(16);
        imageViewStop.setFitWidth(16);
        btnStop.setGraphic(imageViewStop);

        btnStop.setOnAction(event -> {
                    timerInstance.remainingTimeProperty().setValue(0);
                }
        );
        btnStop.setVisible(false);
        grid.add(slider, 0, 1);
        grid.add(startTimerButton, 0, 2);
        grid.add(btnStop, 0, 2);
        grid.add(timerLabel, 0, 0, 3, 1);


        return grid;
    }

    public GridPane addFinishedTimer() {
        backButton.setVisible(true);
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(0, 5, 0, 5));

        Label timerCompletedLabel = new Label("Timer completed");

        timerCompletedLabel.setMaxWidth(113);
        timerCompletedLabel.setWrapText(true);

        timerCompletedLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        grid.add(timerCompletedLabel, 0, 0);


        return grid;
    }

    public GridPane addStepCounter() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(16, 16, 16, 32));
        Button playButton = new Button();
        Image imagePlay = new Image(getClass().getResourceAsStream("resources/play-button.png"));
        ImageView imageViewPlay = new ImageView(imagePlay);
        imageViewPlay.setFitHeight(32);
        imageViewPlay.setFitWidth(32);
        playButton.setGraphic(imageViewPlay);

        playButton.setOnAction((actionEvent) -> {
            mainBorderPane.setCenter(addActiveStepCounter());
        });
        GridPane.setHalignment(playButton, HPos.CENTER);
        GridPane.setValignment(playButton, VPos.CENTER);
        grid.add(playButton, 0, 1);


        return grid;
    }

    public GridPane addActiveStepCounter() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(0, 5, 0, 5));

        Label stepCountLabel = new Label("0 steps");
        stepCountLabel.setWrapText(true);

        stepCountLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        Button btnStop = new Button();
        Image imageStop = new Image(getClass().getResourceAsStream("resources/stop.png"));
        ImageView imageViewStop = new ImageView(imageStop);
        imageViewStop.setFitHeight(16);
        imageViewStop.setFitWidth(16);
        btnStop.setGraphic(imageViewStop);

        btnStop.setOnAction((actionEvent) -> {
            stepHistory.stepCounter.stopCounter();
            mainBorderPane.setCenter(addStoppedStepCounter());
        });

        Button btnReset = new Button();
        Image imageReset = new Image(getClass().getResourceAsStream("resources/reload.png"));
        ImageView imageViewReset = new ImageView(imageReset);
        imageViewReset.setFitHeight(16);
        imageViewReset.setFitWidth(16);
        btnReset.setGraphic(imageViewReset);

        btnReset.setOnAction((actionEvent) -> {
            stepHistory.stepCounter.resetSteps();
        });


        grid.add(stepCountLabel, 0, 0, 3, 1);
        grid.add(btnStop, 0, 2);
        grid.add(btnReset, 1, 2);

        if (stepHistory.stepCounter.startCounter()) {
            stepHistory.stepCounter.numStepsProperty().addListener((observable, oldValue, newValue) -> {
                Platform.runLater(() -> stepCountLabel.setText(String.format("%d steps", newValue.intValue())));
            });
        }

        return grid;
    }

    public GridPane addStoppedStepCounter() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(0, 5, 0, 5));

        Button btnSave = new Button();
        Image imageSave = new Image(getClass().getResourceAsStream("resources/save.png"));
        ImageView imageViewSave = new ImageView(imageSave);
        imageViewSave.setFitHeight(16);
        imageViewSave.setFitWidth(16);
        btnSave.setGraphic(imageViewSave);

        Label stepCountLabel = new Label(String.format("%d steps", stepHistory.stepCounter.getNumSteps()));
        stepCountLabel.setMaxWidth(113);
        stepCountLabel.setWrapText(true);

        stepCountLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        btnSave.setOnAction((actionEvent) -> {
            stepHistory.addDailyCount(stepHistory.stepCounter.saveDailyCount());
            stepCountLabel.setText("Saved successfully");
        });
        grid.add(stepCountLabel, 0, 0);
        grid.add(btnSave, 0, 1);
        return grid;
    }

    public GridPane addHomeScreen() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(0, 5, 0, 5));
        Button stopWatchButton = new Button();
        Image imageTimer = new Image(getClass().getResourceAsStream("resources/stopwatch.png"));
        ImageView imageViewTimer = new ImageView(imageTimer);
        imageViewTimer.setFitHeight(16);
        imageViewTimer.setFitWidth(16);
        stopWatchButton.setGraphic(imageViewTimer);
        stopWatchButton.setOnAction((actionEvent) ->
                {
                    mainBorderPane.setCenter(addTimer());
                    backButton.setVisible(true);
                }
        );
        grid.add(stopWatchButton, 0, 1);

        Button heartMonitorButton = new Button();
        Image imageHeartMonitor = new Image(getClass().getResourceAsStream("resources/heartbeat.png"));
        ImageView imageViewHeartMonitor = new ImageView(imageHeartMonitor);
        imageViewHeartMonitor.setFitHeight(16);
        imageViewHeartMonitor.setFitWidth(16);
        heartMonitorButton.setGraphic(imageViewHeartMonitor);
        heartMonitorButton.setOnAction((actionEvent) ->
                {
                    mainBorderPane.setCenter(addHeartMonitor());
                    backButton.setVisible(true);
                }
        );
        grid.add(heartMonitorButton, 2, 1);

        Button stepCounterButton = new Button();

        Image imageStepTracker = new Image(getClass().getResourceAsStream("resources/stairs.png"));
        ImageView imageViewStepTracker = new ImageView(imageStepTracker);
        imageViewStepTracker.setFitHeight(16);
        imageViewStepTracker.setFitWidth(16);
        stepCounterButton.setGraphic(imageViewStepTracker);
        stepCounterButton.setOnAction((actionEvent) -> {
            mainBorderPane.setCenter(addStepCounter());
            backButton.setVisible(true);
        });
        grid.add(stepCounterButton, 1, 2);

        Button sleepTrackerButton = new Button();
        Image imageSleepTracker = new Image(getClass().getResourceAsStream("resources/sleep.png"));
        ImageView imageViewSleepTracker = new ImageView(imageSleepTracker);
        imageViewSleepTracker.setFitHeight(16);
        imageViewSleepTracker.setFitWidth(16);
        sleepTrackerButton.setGraphic(imageViewSleepTracker);
        sleepTrackerButton.setOnAction((actionEvent) ->
                {
                    mainBorderPane.setCenter(addSleepTracker());
                    backButton.setVisible(true);
                }
        );
        grid.add(sleepTrackerButton, 0, 3);

        Button syncButton = new Button();
        Image imageSync = new Image(getClass().getResourceAsStream("resources/synchronization-arrows.png"));
        ImageView imageViewSync = new ImageView(imageSync);
        imageViewSync.setFitHeight(16);
        imageViewSync.setFitWidth(16);
        syncButton.setGraphic(imageViewSync);
        syncButton.setOnAction((actionEvent) -> {
                    mainBorderPane.setCenter(addSyncData());
                    backButton.setVisible(true);
                }
        );
        grid.add(syncButton, 2, 3);

        BackgroundFill gridBackground = new BackgroundFill(Color.BLACK, new CornerRadii(1),
                new Insets(0.0, 0.0, 0.0, 0.0));
        grid.setBackground(new Background(gridBackground));

        return grid;
    }
}
