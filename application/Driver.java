package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Optional;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Driver extends Application {

	private File file;
	private HashTable hashTable;
	private Date selectedDate;
	private TableView<DateStat> datesTV;

	// martyrs fields
	private TextField nameTxt;
	private DatePicker datePick;
	private TextField ageTxt;
	private TextField distTxt;
	private TextField locTxt;
	private RadioButton maleRadio;
	private RadioButton femaleRadio;
	private Button insertBtn;
	private Button deleteBtn;
	private Button updateBtn;
	private TableView<Martyr> martyrTV;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage stg) throws Exception {
		// TODO Auto-generated method stub
		Button load = new Button("load file");
		VBox vb = new VBox(20, load);
		vb.setAlignment(Pos.CENTER);
		load.setOnAction(e -> {
			FileChooser fch = new FileChooser();
			file = fch.showOpenDialog(stg);
			loadData();
			vb.setVisible(false);
			showDateScreen(stg);
			int count = 0;

			for (int i = 0; i < hashTable.getSize(); i++) {
				if (hashTable.get(i).getData() == null) {
					count++;
				}
			}
			System.out.println("count of nulls : " + count);
			System.out.println("hash size =" + hashTable.getSize());
		});

		Scene sc = new Scene(vb, 300, 250);
		stg.setScene(sc);
		stg.show();
	}

	private void showDateScreen(Stage stg) {
		Pane root = new Pane();
		root.setPrefSize(756, 483);

		DatePicker datePicker = new DatePicker();
		datePicker.setLayoutX(356);
		datePicker.setLayoutY(54);
		datePicker.setPromptText("choose a date");
		datePicker.setOnAction(e -> {
			LocalDate mydate = datePicker.getValue();
			int day = mydate.getDayOfMonth();
			int month = mydate.getMonthValue();
			int year = mydate.getYear();
			String date = month + "/" + day + "/" + year;
		});

		Button insertDateButton = new Button("insert new Date");
		insertDateButton.setLayoutX(221);
		insertDateButton.setLayoutY(54);
		insertDateButton.setOnAction(e -> {
			LocalDate mydate = datePicker.getValue();
			int day = mydate.getDayOfMonth();
			int month = mydate.getMonthValue();
			int year = mydate.getYear();
			String date = month + "/" + day + "/" + year;
			Date dateObject = new Date(date);
			if (hashTable.search(dateObject) != -1) {
				displayAlert("Date " + date + " Already Exists ! ", "e");
			} else {
				Martyr m = new Martyr();
				hashTable.insert(dateObject, m);
				System.out.println(dateObject);
				// System.out.println(hashTable.toString());
				showDates();
			}
		});

		datesTV = new TableView();
		datesTV.setLayoutX(110);
		datesTV.setLayoutY(102);
		datesTV.setPrefSize(536, 237);

		TableColumn<DateStat, String> dateColumn = new TableColumn("Date");
		dateColumn.setPrefWidth(90.67);
		TableColumn<DateStat, String> districtColumn = new TableColumn("District of Max");
		districtColumn.setPrefWidth(110.67);
		TableColumn<DateStat, String> locationColumn = new TableColumn("Location of Max");
		locationColumn.setPrefWidth(109.33);
		TableColumn<DateStat, Integer> totalMartyrsColumn = new TableColumn("Total Martyrs");
		totalMartyrsColumn.setPrefWidth(94.67);
		TableColumn<DateStat, Double> avgMartyrsAgesColumn = new TableColumn("AVG martyrs ages");
		avgMartyrsAgesColumn.setPrefWidth(128.0);

		datesTV.getColumns().addAll(dateColumn, districtColumn, locationColumn, totalMartyrsColumn,
				avgMartyrsAgesColumn);
		dateColumn.setCellValueFactory(new PropertyValueFactory<DateStat, String>("date"));
		districtColumn.setCellValueFactory(new PropertyValueFactory<DateStat, String>("maxDis"));
		locationColumn.setCellValueFactory(new PropertyValueFactory<DateStat, String>("maxLoc"));
		totalMartyrsColumn.setCellValueFactory(new PropertyValueFactory<DateStat, Integer>("totalMartyrs"));
		avgMartyrsAgesColumn.setCellValueFactory(new PropertyValueFactory<DateStat, Double>("avgAges"));
		datesTV.setOnMouseClicked(e -> {
			handleRowSelectionOfDates(e);
		});

		ToggleGroup radioGroup = new ToggleGroup();

		RadioButton deleteRadioButton = new RadioButton("Delete Date");
		deleteRadioButton.setLayoutX(285);
		deleteRadioButton.setLayoutY(355);
		deleteRadioButton.setTextFill(Color.RED);
		deleteRadioButton.setToggleGroup(radioGroup);

		deleteRadioButton.setOnAction(e -> {
			int index = datesTV.getSelectionModel().getSelectedIndex();
			if (index <= -1) {
				return;
			}
			DateStat ds = datesTV.getSelectionModel().getSelectedItem();
			Date selectedDate = new Date(ds.getDate());
			if (ds != null) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setContentText("Are You Sure ?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					System.out.println("Search --> " + hashTable.get(hashTable.search(selectedDate)));
					System.out.println(hashTable.delete(selectedDate));
					showDates();
				}
			}
		});

		RadioButton updateRadioButton = new RadioButton("Update Date");
		updateRadioButton.setLayoutX(399);
		updateRadioButton.setLayoutY(355);
		updateRadioButton.setTextFill(Color.RED);
		updateRadioButton.setToggleGroup(radioGroup);
		updateRadioButton.setOnAction(e -> {
			int index = datesTV.getSelectionModel().getSelectedIndex();
			if (index <= -1) {
				return;
			}
			DateStat ds = datesTV.getSelectionModel().getSelectedItem();
			Date selectedDate = new Date(ds.getDate());
			if (ds != null) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setContentText("Are You Sure ?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {

				}
			}

		});
		ToggleGroup radioGroup2 = new ToggleGroup();
		RadioButton includingbtn = new RadioButton("Print including empty spots.");
		includingbtn.setLayoutX(275);
		includingbtn.setLayoutY(365);
		includingbtn.setTextFill(Color.RED);
		includingbtn.setToggleGroup(radioGroup2);
		includingbtn.setOnAction(e -> {
			showDates();
		});

		RadioButton excludingbtn = new RadioButton("Print excluding empty spots.");
		excludingbtn.setLayoutX(289);
		excludingbtn.setLayoutY(365);
		excludingbtn.setTextFill(Color.RED);
		excludingbtn.setToggleGroup(radioGroup2);
		excludingbtn.setOnAction(e -> {
			showNotNullDates();
		});
		HBox printHbox = new HBox(20, includingbtn, excludingbtn);
		printHbox.setLayoutX(225);
		printHbox.setLayoutY(385);

		Rectangle headerRect = new Rectangle(756, 33);
		headerRect.setArcWidth(5);
		headerRect.setArcHeight(5);
		headerRect.setFill(Color.web("#2f0c0c"));
		headerRect.setStroke(Color.BLACK);
		headerRect.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
		headerRect.setLayoutY(-2);

		Text dateScreenText = new Text("Date Screen");
		dateScreenText.setFill(Color.web("#fffefe"));
		dateScreenText.setLayoutX(14);
		dateScreenText.setLayoutY(19);
		dateScreenText.setCursor(javafx.scene.Cursor.HAND);

		Text martyrsScreenText = new Text("Martyrs Screen");
		martyrsScreenText.setFill(Color.web("#fffefe"));
		martyrsScreenText.setLayoutX(110);
		martyrsScreenText.setLayoutY(19);
		martyrsScreenText.setCursor(javafx.scene.Cursor.HAND);
		martyrsScreenText.setOnMouseClicked(e -> {
			root.setVisible(false);
			showMartyrScreen(stg);
		});

		root.getChildren().addAll(datePicker, insertDateButton, datesTV, deleteRadioButton, updateRadioButton,
				headerRect, dateScreenText, martyrsScreenText, printHbox);

		Scene scene = new Scene(root);
		stg.setScene(scene);
		stg.setTitle("Date Screen");
		stg.show();

		showDates();

	}

	private void handleRowSelectionOfDates(MouseEvent e) {
		int index = datesTV.getSelectionModel().getSelectedIndex();
		if (index <= -1) {
			return;
		}
		DateStat ds = datesTV.getSelectionModel().getSelectedItem();
		Date date = new Date(ds.getDate());
		selectedDate = date;
	}

	private void showDates() {
		ObservableList<DateStat> list = FXCollections.observableArrayList();
		for (int i = 0; i < hashTable.getSize(); i++) {

			if (hashTable.get(i).getData() == null) {
				DateStat dsnull = new DateStat("null", "", "", 0, 0.0);
				list.add(dsnull);
			} else {
				if (hashTable.get(i).getState() != 'D') {
					String date = hashTable.get(i).getData().getDate();
					DateStat ds = new DateStat(date, maxDis(hashTable.get(i).getAvl()),
							maxLoc(hashTable.get(i).getAvl()), totalMartrys(hashTable.get(i).getAvl()),
							avgAges(hashTable.get(i).getAvl()));
					list.add(ds);
				}
			}
		}
		datesTV.setItems(list);
	}

	private void showNotNullDates() {
		ObservableList<DateStat> list = FXCollections.observableArrayList();
		for (int i = 0; i < hashTable.getSize(); i++) {
			if (hashTable.get(i).getData() != null) {
				if (hashTable.get(i).getState() != 'D') {
					String date = hashTable.get(i).getData().getDate();
					DateStat ds = new DateStat(date, maxDis(hashTable.get(i).getAvl()),
							maxLoc(hashTable.get(i).getAvl()), totalMartrys(hashTable.get(i).getAvl()),
							avgAges(hashTable.get(i).getAvl()));
					list.add(ds);
				}
			}
		}
		datesTV.setItems(list);
	}

	private double avgAges(AVL avl) {
		int sum = 0;
		int count = 0;
		if (avl != null) {
			TNode tnode = avl.getRoot();
			if (tnode == null) {
				return 0;
			}
			Queue1 q = new Queue1(100);
			q.enqueue(tnode);
			Queue1 q2 = new Queue1(100);
			while (!q.isEmpty()) {
				TNode m = q.dequeue();
				if (m.getRight() != null)
					q.enqueue(m.getRight());
				if (m.getLeft() != null)
					q.enqueue(m.getLeft());
				q2.enqueue(m);
			}
			while (!q2.isEmpty()) {
				sum += q2.dequeue().getMartyr().getAge();
				count++;
			}
		}
		double avg = sum / (double) count;
		String s = String.format("%.2f", avg);
		return Double.parseDouble(s);
	}

	private int totalMartrys(AVL avl) {
		int total = 0;
		if (avl != null) {
			TNode tnode = avl.getRoot();
			if (tnode == null) {
				return 0;
			}
			Queue1 q = new Queue1(100);
			q.enqueue(tnode);
			Queue1 q2 = new Queue1(100);
			while (!q.isEmpty()) {
				TNode m = q.dequeue();
				if (m.getRight() != null)
					q.enqueue(m.getRight());
				if (m.getLeft() != null)
					q.enqueue(m.getLeft());
				q2.enqueue(m);
			}
			while (!q2.isEmpty()) {
				total++;
				q2.dequeue();
			}
		}
		return total;
	}

	private String maxLoc(AVL avl) {
		int max = 0;
		String maxLoc = "";
		if (avl != null) {
			TNode tnode = avl.getRoot();
			if (tnode == null) {
				return "";
			}
			Queue1 q = new Queue1(100);
			q.enqueue(tnode);
			Queue1 q2 = new Queue1(100);
			while (!q.isEmpty()) {
				TNode m = q.dequeue();
				if (m.getRight() != null)
					q.enqueue(m.getRight());
				if (m.getLeft() != null)
					q.enqueue(m.getLeft());
				q2.enqueue(m);
			}
			while (!q2.isEmpty()) {
				String loc = q2.dequeue().getMartyr().getLocation();
				int numOfMartyrs = getNumberOfMartyrsInLoc(loc, avl);
				if (numOfMartyrs > max) {
					max = numOfMartyrs;
					maxLoc = loc;
				}
			}
		}
		return maxLoc;
	}

	private int getNumberOfMartyrsInLoc(String loc, AVL avl) {
		int total = 0;
		if (avl != null) {
			TNode tnode = avl.getRoot();
			if (tnode == null) {
				return 0;
			}
			Queue1 q = new Queue1(100);
			q.enqueue(tnode);
			Queue1 q2 = new Queue1(100);
			while (!q.isEmpty()) {
				TNode m = q.dequeue();
				if (m.getRight() != null)
					q.enqueue(m.getRight());
				if (m.getLeft() != null)
					q.enqueue(m.getLeft());
				q2.enqueue(m);
			}
			while (!q2.isEmpty()) {
				if (q2.dequeue().getMartyr().getLocation().equalsIgnoreCase(loc)) {
					total++;
				}
			}
		}
		return total;
	}

	private String maxDis(AVL avl) {
		int max = 0;
		String maxDis = "";
		if (avl != null) {
			TNode tnode = avl.getRoot();
			if (tnode == null) {
				return "";
			}
			Queue1 q = new Queue1(100);
			q.enqueue(tnode);
			Queue1 q2 = new Queue1(100);
			while (!q.isEmpty()) {
				TNode m = q.dequeue();
				if (m.getRight() != null)
					q.enqueue(m.getRight());
				if (m.getLeft() != null)
					q.enqueue(m.getLeft());
				q2.enqueue(m);
			}
			while (!q2.isEmpty()) {
				String dis = q2.dequeue().getMartyr().getDistrict();
				int numOfMartyrs = getNumberOfMartyrsInDis(dis, avl);
				if (numOfMartyrs > max) {
					max = numOfMartyrs;
					maxDis = dis;
				}
			}
		}
		return maxDis;
	}

	private int getNumberOfMartyrsInDis(String dis, AVL avl) {
		int total = 0;
		if (avl != null) {
			TNode tnode = avl.getRoot();
			if (tnode == null) {
				return 0;
			}
			Queue1 q = new Queue1(100);
			q.enqueue(tnode);
			Queue1 q2 = new Queue1(100);
			while (!q.isEmpty()) {
				TNode m = q.dequeue();
				if (m.getRight() != null)
					q.enqueue(m.getRight());
				if (m.getLeft() != null)
					q.enqueue(m.getLeft());
				q2.enqueue(m);
			}
			while (!q2.isEmpty()) {
				if (q2.dequeue().getMartyr().getDistrict().equalsIgnoreCase(dis)) {
					total++;
				}
			}
		}
		return total;
	}

	private void loadData() {
		hashTable = new HashTable();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			br.readLine();
			while (((line = br.readLine()) != null)) {
				String[] demo = line.split(",");
				String gender = demo[5].trim();
				String district = demo[4].trim().toLowerCase();
				String location = demo[3].trim().toLowerCase();
				int age;
				try {
					age = Integer.parseInt(demo[2]);
				} catch (Exception e) {
					age = 0;
				}
				String date = demo[1].trim();
				String Name = demo[0].trim();
				Martyr m = new Martyr(Name, date, age, location, district, gender);
				Date dateobj = new Date(date);
				hashTable.insert(dateobj, m);

			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void showMartyrScreen(Stage stg) {
		Pane root = new Pane();
		root.setPrefSize(756, 483);

		VBox vBox = new VBox(15);
		vBox.setAlignment(javafx.geometry.Pos.CENTER);
		vBox.setLayoutX(21);
		vBox.setLayoutY(44);
		vBox.setPrefSize(162, 241);

		nameTxt = new TextField();
		nameTxt.setPromptText("enter name");

		datePick = new DatePicker();
		datePick.setPrefSize(168, 25);
		datePick.setPromptText("choose date of martyrdom");

		ageTxt = new TextField();
		ageTxt.setPromptText("enter age");

		distTxt = new TextField();
		distTxt.setPromptText("enter District");

		locTxt = new TextField();
		locTxt.setPromptText("enter location");

		HBox hBox = new HBox(15);
		hBox.setAlignment(javafx.geometry.Pos.CENTER);
		hBox.setPrefSize(162, 47);

		ToggleGroup genderToggleGroup = new ToggleGroup();

		maleRadio = new RadioButton("Male");
		maleRadio.setToggleGroup(genderToggleGroup);

		femaleRadio = new RadioButton("Female");
		femaleRadio.setToggleGroup(genderToggleGroup);

		hBox.getChildren().addAll(maleRadio, femaleRadio);
		vBox.getChildren().addAll(nameTxt, datePick, ageTxt, distTxt, locTxt, hBox);

		insertBtn = new Button("insert Martyr");
		insertBtn.setLayoutX(60);
		insertBtn.setLayoutY(303);
		insertBtn.setOnAction(e -> {
			String name = nameTxt.getText();
			String dis = distTxt.getText();
			String loc = locTxt.getText();

			if (ageTxt.getText().isEmpty() || name.isEmpty() || dis.isEmpty() || loc.isEmpty()
					|| datePick.getValue() == null || (!femaleRadio.isSelected() && !maleRadio.isSelected())) {
				displayAlert("Please Fill All Information", "e");
			} else {
				int age = Integer.parseInt(ageTxt.getText());
				String gender;
				if (femaleRadio.isSelected()) {
					gender = "f";
				} else {
					gender = "m";
				}
				Martyr m = new Martyr(name, selectedDate.getDate(), age, loc, dis, gender);
				hashTable.get(hashTable.search(selectedDate)).getAvl().insert(m);
				showDates();
				showMartyrs();
			}
		});

		deleteBtn = new Button("delete Martyr");
		deleteBtn.setLayoutX(58);
		deleteBtn.setLayoutY(351);
		deleteBtn.setOnAction(e -> {
			int index = martyrTV.getSelectionModel().getSelectedIndex();
			if (index <= -1) {
				return;
			}
			Martyr m = martyrTV.getSelectionModel().getSelectedItem();
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText("You are about deleting all info about " + m.getName()
					+ " Martyr  \n press ok if you want to delete or cancle if not");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				hashTable.get(hashTable.search(selectedDate)).getAvl().delete(m);
				;
				showDates();
				showMartyrs();
			}

		});

		updateBtn = new Button("update Martyr");
		updateBtn.setLayoutX(56);
		updateBtn.setLayoutY(398);

		martyrTV = new TableView();
		martyrTV.setLayoutX(231);
		martyrTV.setLayoutY(38);
		martyrTV.setPrefSize(508, 233);

		TableColumn<Martyr, String> nameColumn = new TableColumn("Name");
		nameColumn.setPrefWidth(129);

		TableColumn<Martyr, String> dateColumn = new TableColumn("Date");
		dateColumn.setPrefWidth(76.67);

		TableColumn<Martyr, Integer> ageColumn = new TableColumn("Age");
		ageColumn.setPrefWidth(51.33);

		TableColumn<Martyr, String> districtColumn = new TableColumn("District");
		districtColumn.setPrefWidth(92.67);

		TableColumn<Martyr, String> locationColumn = new TableColumn("Location");
		locationColumn.setPrefWidth(98);

		TableColumn<Martyr, String> genderColumn = new TableColumn("Gender");
		genderColumn.setPrefWidth(61.33);

		martyrTV.getColumns().addAll(nameColumn, dateColumn, ageColumn, districtColumn, locationColumn, genderColumn);
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
		districtColumn.setCellValueFactory(new PropertyValueFactory<>("district"));
		locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
		genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
		showMartyrs();
		martyrTV.setOnMouseClicked(e -> {
			int index = martyrTV.getSelectionModel().getSelectedIndex();
			if (index <= -1) {
				return;
			}
			Martyr m = martyrTV.getSelectionModel().getSelectedItem();
			nameTxt.setText(m.getName());
			ageTxt.setText(String.valueOf(m.getAge()));
			distTxt.setText(m.getDistrict());
			locTxt.setText(m.getLocation());
			datePick.setPromptText(m.getDate());
			if (m.getGender().equalsIgnoreCase("m")) {
				maleRadio.setSelected(true);
			} else {
				femaleRadio.setSelected(true);
			}

		});

		VBox buttonsVBox = new VBox(10);
		buttonsVBox.setLayoutX(430);
		buttonsVBox.setLayoutY(300);
		buttonsVBox.setAlignment(Pos.CENTER);

		Button showHeightBtn = new Button("show tree height");
		Button showSizeBtn = new Button("show tree size");
		Button heapSort = new Button("Heap Sort");
		Button saveAsBtn = new Button("Save as");
		Button saveBtn = new Button("Save");

		showHeightBtn.setOnAction(e -> {
			if (selectedDate != null) {
				if (selectedDate.getDate() != null) {
					int index = hashTable.search(new Date(selectedDate.getDate()));
					if (index != -1) {
						AVL avl = hashTable.get(hashTable.search(selectedDate)).getAvl();
						displayAlert("Tree Height = " + avl.getHeight(avl.root), "i");
					}
				} else {
					displayAlert("Tree is Empty ! :\n Height = -1", "i");
				}
			}

		});
		showSizeBtn.setOnAction(e -> {
			if (selectedDate != null) {
				if (selectedDate.getDate() != null) {
					int index = hashTable.search(new Date(selectedDate.getDate()));
					if (index != -1) {
						displayAlert("Tree Size = " + hashTable.get(hashTable.search(selectedDate)).getAvl().getSize(),
								"i");
					}
				} else {
					displayAlert("Tree Size = 0", "i");
				}
			}
		});
		saveAsBtn.setOnAction(e -> {
			Stage stage = new Stage();
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
			fileChooser.setTitle("Save Data");
			File file2 = fileChooser.showSaveDialog(stage);
			if (file2 != null) {
				saveDataToFile(file2);
			}
		});
		saveBtn.setOnAction(e -> {
			saveDataToFile(file);
			displayAlert("Data Saved Succsessfully ", "i");
		});
		heapSort.setOnAction(e -> {
			if (selectedDate != null) {
				if (selectedDate.getDate() != null) {
					if (hashTable.search(selectedDate) > 1) {
						AVL avl = hashTable.get(hashTable.search(selectedDate)).getAvl();
						if (avl != null) {
							TNode tnode = avl.getRoot();
							if (tnode == null) {
								return;
							}
							Queue1 q = new Queue1(100);
							q.enqueue(tnode);
							Queue1 q2 = new Queue1(100);
							while (!q.isEmpty()) {
								TNode m = q.dequeue();
								if (m.getRight() != null)
									q.enqueue(m.getRight());
								if (m.getLeft() != null)
									q.enqueue(m.getLeft());
								q2.enqueue(m);
							}
							MinHeap heap = new MinHeap();
							while (!q2.isEmpty()) {
								heap.insert(q2.dequeue().getMartyr());
							}
							heap.heapSort();
							ObservableList<Martyr> list = FXCollections.observableArrayList();
							for (int i = heap.getSize(); i >= 0; i--) {
								list.add(heap.getElements()[i]);
								heap.setSize(heap.getSize() - 1);
							}
							martyrTV.setItems(list);
						}
					}
				}
			}
		});

		buttonsVBox.getChildren().addAll(showHeightBtn, showSizeBtn, heapSort, saveAsBtn, saveBtn);
		Rectangle headerRect = new Rectangle(756, 33);
		headerRect.setArcWidth(5);
		headerRect.setArcHeight(5);
		headerRect.setFill(Color.web("#2f0c0c"));
		headerRect.setStroke(Color.BLACK);
		headerRect.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
		headerRect.setLayoutY(-2);

		Text dateScreenText = new Text("Date Screen");
		dateScreenText.setFill(Color.web("#fffefe"));
		dateScreenText.setLayoutX(14);
		dateScreenText.setLayoutY(19);
		dateScreenText.setCursor(javafx.scene.Cursor.HAND);
		dateScreenText.setOnMouseClicked(e -> {
			root.setVisible(false);
			showDateScreen(stg);
		});

		Text martyrsScreenText = new Text("Martyrs Screen");
		martyrsScreenText.setFill(Color.web("#fffefe"));
		martyrsScreenText.setLayoutX(110);
		martyrsScreenText.setLayoutY(19);
		martyrsScreenText.setCursor(javafx.scene.Cursor.HAND);

		root.getChildren().addAll(vBox, insertBtn, deleteBtn, updateBtn, martyrTV, headerRect, dateScreenText,
				martyrsScreenText, buttonsVBox);

		Scene scene = new Scene(root);
		stg.setScene(scene);
		stg.setTitle("Martyr Screen");
		stg.show();
	}

	private void saveDataToFile(File file2) {
		try (PrintWriter pw = new PrintWriter(new FileWriter(file2))) {
			pw.println("Name,Date,Age,Location,District,gender");
			for (int i = 0; i < hashTable.getSize(); i++) {
				if (hashTable.get(i).getData() != null) {
					if (hashTable.get(i).getState() != 'D') {
						AVL avl = hashTable.get(i).getAvl();
						if (avl != null) {
							TNode tnode = avl.getRoot();
							if (tnode == null) {
								return;
							}
							Queue1 q = new Queue1(100);
							q.enqueue(tnode);
							Queue1 q2 = new Queue1(100);
							while (!q.isEmpty()) {
								TNode m = q.dequeue();
								if (m.getRight() != null)
									q.enqueue(m.getRight());
								if (m.getLeft() != null)
									q.enqueue(m.getLeft());
								q2.enqueue(m);

							}
							while (!q2.isEmpty()) {
								Martyr m = q2.dequeue().getMartyr();
								pw.println(m.getName() + "," + m.getDate() + "," + m.getAge() + "," + m.getLocation()
										+ "," + m.getDistrict() + "," + m.getGender());

							}
						}
					}
				}
			}
		} catch (Exception ex) {

		}
	}

	private void showMartyrs() {
		if (selectedDate != null) {
			if (selectedDate.getDate() != null) {
				ObservableList<Martyr> list = FXCollections.observableArrayList();
				int index = hashTable.search(selectedDate);
				if (index != -1) {
					HNode hnode = hashTable.get(index);
					AVL avl = hnode.getAvl();
					TNode tnode = avl.getRoot();
					if (tnode == null) {
						martyrTV.setItems(list);
						return;
					}
					Queue1 q = new Queue1(100);
					q.enqueue(tnode);
					Queue1 q2 = new Queue1(100);
					while (!q.isEmpty()) {
						TNode m = q.dequeue();
						if (m.getRight() != null)
							q.enqueue(m.getRight());
						if (m.getLeft() != null)
							q.enqueue(m.getLeft());
						q2.enqueue(m);

					}
					while (!q2.isEmpty()) {
						list.add(q2.dequeue().getMartyr());
					}

				}
				martyrTV.setItems(list);
			}
		}
	}

	private void displayAlert(String message, String type) {
		Alert alert;
		if (type == "i") {
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information");
		} else if (type == "e") {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
		} else {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("CONFIRMATION");
		}

		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

}
