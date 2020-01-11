package view;

import java.text.NumberFormat;
import java.util.Locale;

import controller.ClearBoardController;
import controller.FindPathController;
import controller.RandomBoardController;
import controller.ResetBoardController;
import controller.SetBoardController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import javafx.stage.Stage;
import model.algorithm.AlgorithmType;
import model.heuristic.HeuristicType;
import observer.ISubscriber;

public class MainView extends Stage implements ISubscriber{
	private static MainView instance=null;
	private int rows;
	private int columns;
	private int[][] previousStartBoard;
	private boolean isSetBoard=false;
	
	private TextField[][] tfsStart;
	private TextField tfLvl;
	private TextField tfNode;
	private TextField tfCol;
	private TextField tfRow;
	private ComboBox<HeuristicType> cmbHeur;
	private ComboBox<AlgorithmType> cmbAlg;
	private ComboBox<Integer> cmbSpeed;
	private Button btnFind;
	private Button btnSetBoard;
	private Button btnResetBoard;
	private Button btnClearBoard;
	private Button btnRandomBoard;
	private GridPane gpMain;
	private GridPane gp;
	private GridPane gp2;
	private GridPane gp3;
	private GridPane gp4;
	private GridPane gp5;
	private Label lbCol;
	private Label lbRow;
	private Label lblLvl;
	private Label lbNode;
	private Label lbHeur;
	private Label lbAlg;
	private Label lbSpeed;
	private CheckBox cbImage;
	
	private FindPathController findPathController;
	private SetBoardController setBoardController;
	private RandomBoardController randomBoardController;
	private ClearBoardController clearBoardController;
	private ResetBoardController resetBoardController;
	
	public static MainView getInstance() {
		if(instance==null)
			instance=new MainView();	
		return instance;
	}
	
	private MainView()
	{
		this.setTitle("Sliding Puzzle Solver");
		this.getIcons().add(new Image(
			      MainView.class.getResourceAsStream( "images/favicon.png" ))); 
		this.setResizable(false);
		gpMain=new GridPane();
		gpMain.setPadding(new Insets(20));
		
		gp2=new GridPane();
		gp3=new GridPane();
		gp3.setHgap(10);
		gp3.setVgap(10);
		gp3.setPadding(new Insets(20));
		lbCol=new Label("Columns");
		tfCol=new TextField();
		lbRow=new Label("Rows");
		tfRow=new TextField();
		btnSetBoard=new Button("Set Board");
		btnSetBoard.setPrefWidth(100);
		btnResetBoard=new Button("Reset Board");
		btnResetBoard.setDisable(true);
		btnResetBoard.setPrefWidth(100);	
		btnClearBoard=new Button("Clear Board");
		btnClearBoard.setDisable(true);
		btnClearBoard.setPrefWidth(100);	
		btnRandomBoard=new Button("Random Board");
		btnRandomBoard.setDisable(true);
		btnRandomBoard.setPrefWidth(100);	
		tfCol=new TextField();		
		tfRow=new TextField();
		gp3.add(lbRow, 0, 0);
		gp3.add(tfRow, 1, 0);
		gp3.add(lbCol, 0, 1);
		gp3.add(tfCol, 1, 1);		
		gp3.add(btnSetBoard, 0, 2);
		gp3.add(btnRandomBoard, 1, 2);
		gp3.add(btnResetBoard, 0, 3);
		gp3.add(btnClearBoard, 1, 3);
		
		gp2.add(gp3, 0, 0);
		
		gpMain.add(gp2, 1, 0);
		
		setBoardController=new SetBoardController();
		btnSetBoard.setOnAction(setBoardController);
		resetBoardController=new ResetBoardController();
		btnResetBoard.setOnAction(resetBoardController);
		clearBoardController=new ClearBoardController();
		btnClearBoard.setOnAction(clearBoardController);
		randomBoardController=new RandomBoardController();
		btnRandomBoard.setOnAction(randomBoardController);
			
		Scene sc=new Scene(gpMain);
		sc.getStylesheets().add("view/stylesheet.css");
		setScene(sc);
		show();
	}
	
	public void setBoard()
	{
		gpMain.getChildren().remove(gp);
		gp=new GridPane();
		gp.setMaxSize(100, 100);
		gp.setVgap(1);
		gp.setHgap(1);
		gp.setPadding(new Insets(20));
		
		tfsStart=new TextField[rows][columns];
		for(int i=0;i<rows;i++)
			for(int j=0;j<columns;j++)
			{
				tfsStart[i][j]=new TextField();
				tfsStart[i][j].setAlignment(Pos.CENTER);
				tfsStart[i][j].setMinSize(80, 80);
				tfsStart[i][j].getStyleClass().add("tfsStart");
				gp.add(tfsStart[i][j], j, i);
			}
		
		gpMain.add(gp, 0, 0);
		
		//Independent of rows and columns
		if(!isSetBoard)
		{
			gp4=new GridPane();
			gp4.setHgap(10);
			gp4.setVgap(10);
			gp4.setPadding(new Insets(20));
			lblLvl=new Label("Moves");
			lbNode=new Label("Nodes explored");
			lbHeur=new Label("Heuristic");
			lbAlg=new Label("Algorithm");
			cmbHeur=new ComboBox<HeuristicType>();
			cmbHeur.getItems().add(HeuristicType.Hamming);
			cmbHeur.getItems().add(HeuristicType.Manhatten);		
			cmbHeur.getItems().add(HeuristicType.LinearConflict);	
			cmbHeur.getSelectionModel().selectFirst();
			cmbAlg=new ComboBox<AlgorithmType>();
			cmbAlg.getItems().add(AlgorithmType.BFS);
			//cmbAlg.getItems().add(AlgorithmType.DFS);
			cmbAlg.getItems().add(AlgorithmType.AStar);
			cmbAlg.getItems().add(AlgorithmType.IDAStar);
			cmbAlg.getSelectionModel().selectFirst();
			tfLvl=new TextField();
			tfLvl.setEditable(false);
			tfNode=new TextField();
			tfNode.setEditable(false);
			
			gp4.add(lbAlg, 0, 0);
			gp4.add(cmbAlg, 1, 0);
			gp4.add(lbHeur, 0, 1);
			gp4.add(cmbHeur, 1, 1);
			gp4.add(lblLvl, 0, 2);
			gp4.add(lbNode, 0, 3);
			gp4.add(tfLvl, 1, 2);
			gp4.add(tfNode, 1, 3);
				
			gp5=new GridPane();
			gp5.setHgap(10);
			gp5.setVgap(10);
			gp5.setPadding(new Insets(20));
			cbImage=new CheckBox("Image");
			lbSpeed=new Label("Speed (ms)");
			cmbSpeed=new ComboBox<Integer>();
			cmbSpeed.getItems().add(100);
			cmbSpeed.getItems().add(500);
			cmbSpeed.getItems().add(1000);
			cmbSpeed.getSelectionModel().selectFirst();
			btnFind=new Button("Find");
			btnFind.setPrefWidth(100);	
			
			gp5.add(lbSpeed, 0, 1);
			gp5.add(cmbSpeed, 1, 1);
			gp5.add(btnFind, 0, 2);
			
			gp2.add(gp4, 0, 1);
			gp2.add(gp5, 0, 2);
			
			findPathController=new FindPathController();
			btnFind.setOnAction(findPathController);
			
			isSetBoard=true;
		}
		
		//Dependent of rows and columns
		cmbAlg.getItems().remove(AlgorithmType.TileByTile);
		if(rows>3)
			cmbAlg.getItems().add(AlgorithmType.TileByTile);
		
		cbImage.setSelected(false);
		gp5.getChildren().remove(cbImage);
		if(rows==columns && rows<7)
			gp5.add(cbImage, 0, 0);	
		
		previousStartBoard=null;
	}
	
	public int[][] getTfsStart() {
		int[][] toReturn=new int[rows][columns];
		for(int i=0;i<rows;i++)
			for(int j=0;j<columns;j++)
			{
				if(tfsStart[i][j].getText().isEmpty())
					toReturn[i][j]=0;
				else
					toReturn[i][j]=Integer.valueOf(tfsStart[i][j].getText());
			}
		return toReturn;
	}
	
	public void setTfsStart(int[][] board) {
		for(int i=0;i<rows;i++)
			for(int j=0;j<columns;j++)
			{
				if(board[i][j]==0)
					tfsStart[i][j].setText("");
				else
					tfsStart[i][j].setText(String.valueOf(Math.abs(board[i][j])));
			}
	}
	
	public void clearAll() {
		for(int i=0;i<rows;i++)
			for(int j=0;j<columns;j++)
					tfsStart[i][j].setText("");
	}
	
	public void setImages(int [][] board)
	{
		for(int i=0;i<rows;i++)
			for(int j=0;j<columns;j++)
			{
				if(board[i][j]!=0)
				{
					int num=Math.abs(board[i][j])-1;
					int currRow=num/rows;
					int currCol=num%columns;
					tfsStart[i][j].setId("tfsStart"+currRow+currCol);
				}
				else
					tfsStart[i][j].setId("emptyField");

			}
	}
	
	public void setTfNode(int node) {
		this.tfNode.setText(NumberFormat.getNumberInstance(Locale.US).format(node));
	}
	
	public void setTfLvl(int lvl) {
		this.tfLvl.setText(Integer.toString(lvl));
	}
	
	public void enableOrDisableCommands(boolean val)
	{
		tfRow.setEditable(val);
		tfCol.setEditable(val);
		btnFind.setDisable(!val);
		btnSetBoard.setDisable(!val);
		btnClearBoard.setDisable(!val);
		btnResetBoard.setDisable(!val);
		btnRandomBoard.setDisable(!val);
		tfNode.setText("");
		tfLvl.setText("");
		
		for(int i=0;i<rows;i++)
			for(int j=0;j<columns;j++)
				tfsStart[i][j].setEditable(val);
	}
	
	public HeuristicType getCmbHeur() {
		return cmbHeur.getSelectionModel().getSelectedItem();
	}
	
	public String getTfRow() {
		return tfRow.getText();
	}
	
	public String getTfCol() {
		return tfCol.getText();
	}
	
	public GridPane getGpMain() {
		return gpMain;
	}
	
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getColumns() {
		return columns;
	}
	
	public void setPreviousStartBoard(int[][] previousStartBoard) {
		this.previousStartBoard = previousStartBoard;
	}
	
	public int[][] getPreviousStartBoard() {
		return previousStartBoard;
	}
	
	public FindPathController getFindPathController() {
		return findPathController;
	}
	
	public AlgorithmType getCmbAlg() {
		return cmbAlg.getSelectionModel().getSelectedItem();
	}

	@Override
	public void update(int nodes) {
		//Updated GUI on main thread
		Platform.runLater(new Runnable() {
			
		@Override
		public void run() {
			setTfNode(nodes);		
		}});
	}
	
	public boolean getCbImage() {
		return cbImage.isSelected();
	}
	
	public int getCmbSpeed() {
		return cmbSpeed.getSelectionModel().getSelectedItem();
	}
}
