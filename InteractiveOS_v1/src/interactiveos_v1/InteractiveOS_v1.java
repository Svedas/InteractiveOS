/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interactiveos_v1;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Mantas
 */
public class InteractiveOS_v1 extends Application {

    private TextField consoleOutputText;
    private TextField consoleInputText;

    private RealMachine rm;
    private VirtualMachine vm;

    private GridPane vmMemGrid;

    private boolean needToClearOutput = false;
    private boolean programFinished = false;
    private File sourceCode = null;
    private String input = null;
    @Override
    public void start(Stage primaryStage) {

        //Root pane
        BorderPane root = new BorderPane();

        //Real Mashine
        rm = new RealMachine();

        //Virtual Machine
        vm = new VirtualMachine(rm);

        //OS startup
        System.out.println("OS loading");
        //rm.startVirtualMachine(vm);

////////////////////////////////////////////////////////////////////////////////
        //********************************************************************//
        //Left pane
        VBox leftPane = new VBox();
        //leftPane.setAlignment(Pos.CENTER);
        leftPane.setSpacing(16);
        leftPane.setPadding(new Insets(32, 32, 32, 32));
        //leftPane.setAlignment(Pos.CENTER);

        //RM CPU label
        Label rmCpuLabel = new Label("RM processor");
        rmCpuLabel.setTextFill(Color.NAVY);

        //RM CPU grid
        GridPane rmCpuGrid = new GridPane();
        rmCpuGrid.gridLinesVisibleProperty().setValue(Boolean.TRUE);

        rmCpuGrid.add(new Label("PTR"), 0, 0);
        Label ptr = new Label("x");
        ptr.textProperty().bind(rm.cpu.ptrProperty());
        rmCpuGrid.add(ptr, 1, 0);

        rmCpuGrid.add(new Label("R1"), 0, 1);
        Label r1 = new Label("x");
        r1.textProperty().bind(vm.cpu.r1Property()); //RM or VM?
        rmCpuGrid.add(r1, 1, 1);

        rmCpuGrid.add(new Label("R2"), 0, 2);
        Label r2 = new Label("x");
        r2.textProperty().bind(vm.cpu.r2Property());
        rmCpuGrid.add(r2, 1, 2);

        rmCpuGrid.add(new Label("R3"), 0, 3);
        Label r3 = new Label("x");
        r3.textProperty().bind(vm.cpu.r3Property());
        rmCpuGrid.add(r3, 1, 3);

        rmCpuGrid.add(new Label("IC"), 0, 4);
        Label ic = new Label("x");
        ic.textProperty().bind(vm.cpu.icProperty());
        rmCpuGrid.add(ic, 1, 4);

        rmCpuGrid.add(new Label("MODE"), 0, 5);
        Label mode = new Label("x");
        mode.textProperty().bind(rm.cpu.modeProperty());
        rmCpuGrid.add(mode, 1, 5);

        rmCpuGrid.add(new Label("SF"), 0, 6);
        Label sf = new Label("x");
        sf.textProperty().bind(vm.cpu.sfProperty());
        rmCpuGrid.add(sf, 1, 6);

        rmCpuGrid.add(new Label("TI"), 0, 7);
        Label ti = new Label("x");
        ti.textProperty().bind(rm.cpu.tiProperty());
        rmCpuGrid.add(ti, 1, 7);

        rmCpuGrid.add(new Label("SI"), 0, 8);
        Label si = new Label("x");
        si.textProperty().bind(rm.cpu.siProperty());
        rmCpuGrid.add(si, 1, 8);

        rmCpuGrid.add(new Label("PI"), 0, 9);
        Label pi = new Label("x");
        pi.textProperty().bind(rm.cpu.piProperty());
        rmCpuGrid.add(pi, 1, 9);

        rmCpuGrid.add(new Label("CH1"), 0, 10);
        Label ch1 = new Label("x");
        ch1.textProperty().bind(rm.cpu.ch1Property());
        rmCpuGrid.add(ch1, 1, 10);

        rmCpuGrid.add(new Label("CH2"), 0, 11);
        Label ch2 = new Label("x");
        ch2.textProperty().bind(rm.cpu.ch2Property());
        rmCpuGrid.add(ch2, 1, 11);

        rmCpuGrid.add(new Label("CH3"), 0, 12);
        Label ch3 = new Label("x");
        ch3.textProperty().bind(rm.cpu.ch3Property());
        rmCpuGrid.add(ch3, 1, 12);

        //VM CPU label
        Label vmCpuLabel = new Label("VM processor");
        vmCpuLabel.setTextFill(Color.NAVY);

        //VM CPU grid
        GridPane vmCpuGrid = new GridPane();
        vmCpuGrid.gridLinesVisibleProperty().setValue(Boolean.TRUE);

        vmCpuGrid.add(new Label("R1"), 0, 0);
        Label vmR1 = new Label("x");
        vmR1.textProperty().bind(vm.cpu.r1Property());
        vmCpuGrid.add(vmR1, 1, 0);

        vmCpuGrid.add(new Label("R2"), 0, 1);
        Label vmR2 = new Label("x");
        vmR2.textProperty().bind(vm.cpu.r2Property());
        vmCpuGrid.add(vmR2, 1, 1);

        vmCpuGrid.add(new Label("FHR"), 0, 2);
        Label vmR3 = new Label("x");
        vmR3.textProperty().bind(vm.cpu.r3Property());
        vmCpuGrid.add(vmR3, 1, 2);

        vmCpuGrid.add(new Label("IC"), 0, 3);
        Label vmIc = new Label("x");
        vmIc.textProperty().bind(vm.cpu.icProperty());
        vmCpuGrid.add(vmIc, 1, 3);

        vmCpuGrid.add(new Label("SF"), 0, 4);
        Label vmSf = new Label("x");
        vmSf.textProperty().bind(vm.cpu.sfProperty());
        vmCpuGrid.add(vmSf, 1, 4);

        //Column constraints
        //for (int i = 0; i < 2; i++){
        ColumnConstraints columnConstr1 = new ColumnConstraints();
        columnConstr1.setMinWidth(48);
        columnConstr1.setMaxWidth(48);
        columnConstr1.setHalignment(HPos.CENTER);
        rmCpuGrid.getColumnConstraints().add(columnConstr1);
        vmCpuGrid.getColumnConstraints().add(columnConstr1);
        columnConstr1 = new ColumnConstraints();
        columnConstr1.setMinWidth(96);
        columnConstr1.setMaxWidth(96);
        columnConstr1.setHalignment(HPos.CENTER);
        rmCpuGrid.getColumnConstraints().add(columnConstr1);
        vmCpuGrid.getColumnConstraints().add(columnConstr1);
        //}

        ////////////////////////////////////////////////////////////////////////
        VBox controlButtons = new VBox();
        controlButtons.setSpacing(8);
        controlButtons.setPadding(new Insets(32, 0, 32, 0));
        int btnWidth = 96;

        Button run = new Button("Run");
        run.setStyle("-fx-border-color: Green; -fx-border-width: 2px;");
        run.setMaxWidth(btnWidth);
        run.setMinWidth(btnWidth);
        run.setOnAction((ActionEvent event) -> {
            // run program
            if (sourceCode == null) {
                return;
            }
            while (!programFinished) {
                executeVmStep(vm, rm);
            }
        });

        Button step = new Button("Step");
        step.setStyle("-fx-border-color: Yellow; -fx-border-width: 2px;");
        step.setMaxWidth(btnWidth);
        step.setMinWidth(btnWidth);
        step.setOnAction((ActionEvent event) -> {
            // step program
            if (sourceCode == null) {
                return;
            }
            executeVmStep(vm, rm);
        });

        Button reset = new Button("Reset");
        reset.setStyle("-fx-border-color: Red; -fx-border-width: 2px;");
        reset.setMaxWidth(btnWidth);
        reset.setMinWidth(btnWidth);
        reset.setOnAction((ActionEvent event) -> {
            // reset program
            if (sourceCode == null) {
                return;
            }

            rm.clearRegisters();
            vm.ClearRegisters();
            vm.ClearMemory();
            programFinished = false;
            needToClearOutput = true;
            vm.loadProgram(sourceCode);
            consoleOutputText.setText("Reset");
        });

        Button load = new Button("Load");
        load.setStyle("-fx-border-color: Blue; -fx-border-width: 2px;");
        load.setMaxWidth(btnWidth);
        load.setMinWidth(btnWidth);
        load.setOnAction((ActionEvent event) -> {
            rm.startVirtualMachine(vm);
            redrawVmGrid();

            FileChooser browser = new FileChooser();
            browser.setInitialDirectory(new File(System.getProperty("user.dir")));
            sourceCode = browser.showOpenDialog(primaryStage);
            if (sourceCode == null) {
                return;
            }
            consoleOutputText.setText(sourceCode.toString());
            needToClearOutput = true;
            programFinished = false;
            // load program
            vm.ClearRegisters();
            vm.ClearMemory();
            vm.loadProgram(sourceCode);
        });

        controlButtons.getChildren().add(run);
        controlButtons.getChildren().add(step);
        controlButtons.getChildren().add(reset);
        controlButtons.getChildren().add(load);

        //********************************************************************//
        ////////////////////////////////////////////////////////////////////////
        leftPane.getChildren().add(rmCpuLabel);
        leftPane.getChildren().add(rmCpuGrid);
        leftPane.getChildren().add(vmCpuLabel);
        leftPane.getChildren().add(vmCpuGrid);
        leftPane.getChildren().add(controlButtons);

////////////////////////////////////////////////////////////////////////////////
        //********************************************************************//
        //Right pane
        VBox rightPane = new VBox();
        rightPane.setSpacing(8);
        //rightPane.setPadding(new Insets(32,0,0,0));

        //RM memory label
        Label rmMemLabel = new Label("\tReal memory");
        //rmMemLabel.setTextAlignment(TextAlignment.CENTER);
        rmMemLabel.setTextFill(Color.NAVY);

        //RM grid labels
        GridPane rmMemLabelGrid = new GridPane();
        rmMemLabelGrid.gridLinesVisibleProperty().setValue(Boolean.TRUE);
        for (int i = 0; i < 16; i++) {
            //Block number
            Label wordLabel = new Label(Integer.toHexString(i).toUpperCase());
            wordLabel.setTextFill(Color.NAVY);
            rmMemLabelGrid.add(wordLabel, 1 + i, 0);
        }

        //RM grid
        GridPane rmMemGrid = new GridPane();
        rmMemGrid.gridLinesVisibleProperty().setValue(Boolean.TRUE);

        //RM words
        for (int block = 0; block < 16 * 5; block++) {
            //Block number
            Label blockNum = new Label(Integer.toHexString(block).toUpperCase());
            blockNum.setTextFill(Color.NAVY);
            rmMemGrid.add(blockNum, 0, block);

            for (int word = 0; word < 16; word++) {
                Label temp = new Label("0");
                temp.textProperty().bind(rm.memoryProperty(block, word));
                rmMemGrid.add(temp, 1 + word, block);
            }
        }

        //VM memory label
        Label vmMemLabel = new Label("\tVirtual memory");
        vmMemLabel.setTextFill(Color.NAVY);

        //VM grid labels
        GridPane vmMemLabelGrid = new GridPane();
        vmMemLabelGrid.gridLinesVisibleProperty().setValue(Boolean.TRUE);
        for (int i = 0; i < 16; i++) {
            //Block number
            Label wordLabel = new Label(Integer.toHexString(i).toUpperCase());
            wordLabel.setTextFill(Color.NAVY);
            vmMemLabelGrid.add(wordLabel, 1 + i, 0);
        }

        //VM grid
        vmMemGrid = new GridPane();
        vmMemGrid.gridLinesVisibleProperty().setValue(Boolean.TRUE);

        //VM words
        for (int block = 0; block < 16; block++) {
            //Block number
            Label blockNum = new Label(Integer.toHexString(block).toUpperCase());
            blockNum.setTextFill(Color.NAVY);
            vmMemGrid.add(blockNum, 0, block);

            for (int word = 0; word < 16; word++) {
                Label temp = new Label("0");
                temp.textProperty().bind(vm.memoryProperty(block, word));
                vmMemGrid.add(temp, 1 + word, block);
            }
        }

        //Column constraints
        int width = 56;

        ColumnConstraints columnConstr0 = new ColumnConstraints();
        columnConstr0.setMinWidth(width / 2);
        columnConstr0.setMaxWidth(width / 2);
        columnConstr0.setHalignment(HPos.CENTER);
        rmMemGrid.getColumnConstraints().add(columnConstr0);
        vmMemGrid.getColumnConstraints().add(columnConstr0);
        rmMemLabelGrid.getColumnConstraints().add(columnConstr0);
        vmMemLabelGrid.getColumnConstraints().add(columnConstr0);

        for (int i = 1; i < 17; i++) {
            ColumnConstraints columnConstr = new ColumnConstraints();
            columnConstr.setMinWidth(width);
            columnConstr.setMaxWidth(width);
            columnConstr.setHalignment(HPos.CENTER);
            rmMemGrid.getColumnConstraints().add(columnConstr);
            vmMemGrid.getColumnConstraints().add(columnConstr);
            rmMemLabelGrid.getColumnConstraints().add(columnConstr);
            vmMemLabelGrid.getColumnConstraints().add(columnConstr);
        }

        //Input label
        Label consoleInputLabel = new Label("\tConsole Input");
        consoleInputLabel.setTextFill(Color.NAVY);

        //Input textfield
        consoleInputText = new TextField();
        consoleInputText.setMinSize(800, 25);
        consoleInputText.setMaxSize(800, 25);
        consoleInputText.setEditable(false);

        consoleInputText.setOnKeyPressed((final KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                input = consoleInputText.getText();
            }
        });
        //Output label
        Label consoleOutputLabel = new Label("\tConsole Output");
        consoleOutputLabel.setTextFill(Color.NAVY);

        //Output textfield
        consoleOutputText = new TextField();
        consoleOutputText.setEditable(false);
        consoleOutputText.setMinSize(800, 25);
        consoleOutputText.setMaxSize(800, 25);

        ////////////////////////////////////////////////////////////////////////
        //RM grid label scroll
        ScrollPane rmLabelScroll = new ScrollPane(rmMemLabelGrid);
        rmLabelScroll.setMaxHeight(20);
        rmLabelScroll.setMinHeight(20);
        rmLabelScroll.setContent(rmMemLabelGrid);
        rmLabelScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        //RM grid scroll
        ScrollPane rmMemScroll = new ScrollPane(rmMemGrid);
        rmMemScroll.setMaxHeight(275);
        rmMemScroll.setMinHeight(275);
        rmMemScroll.setContent(rmMemGrid);
        rmMemScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        //VM grid label scroll
        ScrollPane vmLabelScroll = new ScrollPane(vmMemLabelGrid);
        vmLabelScroll.setMaxHeight(20);
        vmLabelScroll.setMinHeight(20);
        vmLabelScroll.setContent(vmMemLabelGrid);
        vmLabelScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        //VM grid scroll
        ScrollPane vmMemScroll = new ScrollPane(vmMemGrid);
        vmMemScroll.setMaxHeight(135);
        vmMemScroll.setMinHeight(135);
        vmMemScroll.setContent(vmMemGrid);
        vmMemScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        //********************************************************************//
        ////////////////////////////////////////////////////////////////////////
        rightPane.getChildren().add(rmMemLabel);
        rightPane.getChildren().add(rmLabelScroll);
        rightPane.getChildren().add(rmMemScroll);
        rightPane.getChildren().add(vmMemLabel);
        rightPane.getChildren().add(vmLabelScroll);
        rightPane.getChildren().add(vmMemScroll);
        rightPane.getChildren().add(consoleInputLabel);
        rightPane.getChildren().add(consoleInputText);
        rightPane.getChildren().add(consoleOutputLabel);
        rightPane.getChildren().add(consoleOutputText);

        root.setLeft(leftPane);
        root.setRight(rightPane);

        int windowX = 1200;
        int windowY = 720;
        root.setMaxSize(windowX, windowY);
        root.setMinSize(windowX, windowY);
        Scene scene = new Scene(root, windowX, windowY);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    private void executeVmStep(VirtualMachine vm, RealMachine rm) {
        try {
            if (programFinished) {
                return;
            }
            String output = vm.executeStep();
            if (needToClearOutput) {
                consoleOutputText.setText("");
                needToClearOutput = false;
            }
            if (output.equals("$END")) {
                programFinished = true;
                return;
            }
            if (rm.cpu.GetCH2Value() == 0 && !output.isEmpty()) {
                rm.cpu.setCH2(1);
                consoleOutputText.appendText(output);
                rm.cpu.setCH2(0);
            }
            //testInterupts(vm, rm);
            
        } catch (IOException ex) {
            Logger.getLogger(InteractiveOS_v1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void redrawVmGrid() {
        vmMemGrid.getChildren().retainAll(vmMemGrid.getChildren().get(0));
        //vmMemGrid.getChildren().clear();
        //vmMemGrid.gridLinesVisibleProperty().setValue(Boolean.TRUE);
        for (int block = 0; block < 16; block++) {
            //Block number
            Label blockNum = new Label(Integer.toHexString(block).toUpperCase());
            blockNum.setTextFill(Color.NAVY);
            vmMemGrid.add(blockNum, 0, block);

            for (int word = 0; word < 16; word++) {
                Label temp = new Label("0");
                temp.textProperty().bind(vm.memoryProperty(block, word));
                vmMemGrid.add(temp, 1 + word, block);
            }
        }
    }
    void testInterupts(VirtualMachine vm, RealMachine rm)
    {
        switch(rm.cpu.GetSIValue())
        {
            case 4:
            {
                //read
                break;
            }
        }
    }
}
