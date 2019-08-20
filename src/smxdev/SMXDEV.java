
package smxdev;

import javafx.application.Application;              import static javafx.application.Application.launch;
import javafx.scene.*;
import javafx.scene.control.*; import javafx.animation.*;
import javafx.scene.layout.*;
import javafx.stage.*; import javafx.geometry.*; import javafx.scene.image.*;

import java.io.*; import java.util.*;
import javafx.util.Duration;  import javafx.print.*;
//import com.gluonhq.charm.glisten.control.ProgressIndicator;
import java.awt.Desktop;
import java.net.*;
import javafx.scene.paint.Color; import javafx.collections.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.web.*;
//import javafx.stage.FileChooser.ExtensionFilter;
import org.apache.commons.io.FileUtils; 

/**
 *
 * @author SeumX Plus
 */
public class SMXDEV extends Application  {
    private String bg; private double xoff; private double yoff; private Properties prp; private Stage cs;
    private File fll; private String curr; private List<File> lst; private ObservableList<File> obls;
    String[] cbc; String kih; private Stage csi; private FadeTransition fdg,fdg2; private String gl;
    
    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void start(Stage Stage) throws Exception {
        
        //create projects Folder
        File dr=new File("C:/Users/Public/SeumxDevProjects");
        if(!dr.exists()) {
            dr.mkdirs();
        } String prdir="C:/Users/Public/SeumxDevProjects/"; String proj="";  String scn="";
        
        //write projcts File
        File ppr=new File("proj.dll");
        if(ppr.exists()) {
            //reading projects list
            FileInputStream inp=new FileInputStream("proj.dll");
            ObjectInputStream pobj =new ObjectInputStream(inp);
            lst=new ArrayList(); lst=(List) pobj.readObject(); 
            obls=FXCollections.observableArrayList(); obls.addAll(lst);
        } else {
            FileOutputStream oiu=new FileOutputStream(ppr);
            ObjectOutputStream obji=new ObjectOutputStream(oiu);
            lst=new ArrayList(); obji.writeObject(lst); obji.flush(); obji.close(); 
        }
        
        //reading Data
        File apdt=new File("Data.bat");
        if(apdt.exists()) {
            FileInputStream in=new FileInputStream(apdt);
            ObjectInputStream obj=new ObjectInputStream(in);
            prp=(Properties) obj.readObject();
        } else {
            prp=new Properties(); prp.put("theme","navy"); prp.put("oppr",""); prp.put("los","");
            FileOutputStream out=new FileOutputStream(apdt);
            ObjectOutputStream obju=new ObjectOutputStream(out);
            obju.writeObject(prp);
        }
        
        bg="-fx-background-color:"+prp.getProperty("theme")+"";
        Image bgm=new Image(new FileInputStream("SMXD.png")); 
        BackgroundSize bs=new BackgroundSize(270,270,true,true,true,false);
        BackgroundImage bim=new BackgroundImage(bgm,BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT,BackgroundPosition.CENTER,bs);
        Background big=new Background(bim);
        BackgroundFill bf=new BackgroundFill(Color.NAVY,new CornerRadii(0),new Insets(2,2,2,2)); Background bh=new Background(bf);
        
        Image icc=new Image(new FileInputStream("icon.png"));
        Stage.getIcons().add(icc); Stage.setResizable(false);
        Stage.setWidth(1112); Stage.setHeight(677); Stage.initStyle(StageStyle.UNDECORATED);
        
        MenuItem np=new MenuItem("New Project"); MenuItem op=new MenuItem("Open Project"); MenuItem cp=new MenuItem("Close Project");
        MenuItem pp=new MenuItem("Project Properties"); MenuItem xp=new MenuItem("Export Project"); 
        MenuItem sv=new MenuItem("Save"); MenuItem pr=new MenuItem("Print"); MenuItem xt=new MenuItem("Exit");
        MenuItem nf=new MenuItem("New File");
        Menu fl=new Menu("File"); fl.getItems().addAll(nf,np,op,cp,pp,sv,pr,xt);
        
        np.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCodeCombination.CONTROL_DOWN, KeyCodeCombination.SHIFT_DOWN));
        nf.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCodeCombination.CONTROL_DOWN));
        op.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCodeCombination.CONTROL_DOWN));
        cp.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCodeCombination.CONTROL_DOWN));
        pp.setAccelerator(new KeyCodeCombination(KeyCode.T, KeyCodeCombination.CONTROL_DOWN));
        //xp.setAccelerator(new KeyCodeCombination(KeyCode.K, KeyCodeCombination.CONTROL_DOWN));
        sv.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCodeCombination.CONTROL_DOWN));
        pr.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCodeCombination.CONTROL_DOWN));
        xt.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCodeCombination.CONTROL_DOWN));
        
        MenuItem copy=new MenuItem("Copy"); MenuItem cut=new MenuItem("Cut"); MenuItem pst=new MenuItem("Paste");
        MenuItem undo=new MenuItem("Undo"); MenuItem redo=new MenuItem("Redo");
        Menu edit=new Menu("Edit"); edit.getItems().addAll(undo,redo,copy,cut,pst);
        
        copy.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCodeCombination.CONTROL_DOWN));
        cut.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCodeCombination.CONTROL_DOWN));
        pst.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCodeCombination.CONTROL_DOWN));
        undo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCodeCombination.CONTROL_DOWN));
        redo.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCodeCombination.CONTROL_DOWN));
        
        MenuItem lt=new MenuItem("Default"); MenuItem gry=new MenuItem("Grey"); MenuItem grn=new MenuItem("Green"); 
        MenuItem rd=new MenuItem("Red"); MenuItem prl=new MenuItem("Purple"); MenuItem cyn=new MenuItem("Cyan");
        MenuItem pnk=new MenuItem("Pink"); MenuItem gld=new MenuItem("Gold");
        Menu vew=new Menu("Theme"); vew.getItems().addAll(lt,gry,grn,rd,prl,cyn,pnk,gld);
        
        MenuItem bp=new MenuItem("Build Project"); MenuItem rp=new MenuItem("Run Project"); 
        MenuItem cb=new MenuItem("Clean and Build"); Menu rn=new Menu("Run"); rn.getItems().addAll(rp,bp,cb);
        
        bp.setAccelerator(new KeyCodeCombination(KeyCode.F8));
        rp.setAccelerator(new KeyCodeCombination(KeyCode.F7));
        cb.setAccelerator(new KeyCodeCombination(KeyCode.F9));
        
        MenuItem hd=new MenuItem("Restart"); MenuItem cu=new MenuItem("Check For Updates"); 
        MenuItem ab=new MenuItem("About"); MenuItem ri=new MenuItem("Report Issue");
        Menu hlp=new Menu("Help"); hlp.getItems().addAll(hd,cu,ab);
        
        hd.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCodeCombination.CONTROL_DOWN));
        cu.setAccelerator(new KeyCodeCombination(KeyCode.U, KeyCodeCombination.CONTROL_DOWN));
        ab.setAccelerator(new KeyCodeCombination(KeyCode.K, KeyCodeCombination.CONTROL_DOWN));
        
        MenuBar bar=new MenuBar(); bar.setPadding(new Insets(2,2,2,2));
        bar.setStyle("-fx-background-color:lightgrey;-fx-font:normal 14px 'Cambria';");
        bar.getMenus().addAll(fl,edit,vew,rn,hlp);
        
        //Image btns
        Image npm=new Image(new FileInputStream("Buttons/NewP.png"));  ImageView npp=new ImageView(npm);
        Image opm=new Image(new FileInputStream("Buttons/OpenP.png")); ImageView opp=new ImageView(opm);
        Image svm=new Image(new FileInputStream("Buttons/Sv.png"));    ImageView svv=new ImageView(svm);
        Image prm=new Image(new FileInputStream("Buttons/Prp.png"));   ImageView prr=new ImageView(prm);
        Image clm=new Image(new FileInputStream("Buttons/Cls.png"));   ImageView cll=new ImageView(clm);
        Image blm=new Image(new FileInputStream("Buttons/Bld.png"));   ImageView bll=new ImageView(blm);
        Image cbm=new Image(new FileInputStream("Buttons/clbl.png"));  ImageView cbb=new ImageView(cbm);
        Image rnm=new Image(new FileInputStream("Buttons/rn.png"));    ImageView rnn=new ImageView(rnm);
        Image nfl=new Image(new FileInputStream("Buttons/nf.png"));    ImageView nfi=new ImageView(nfl);
        Image und=new Image(new FileInputStream("Buttons/undo.png"));    ImageView un=new ImageView(und);
        Image red=new Image(new FileInputStream("Buttons/redo.png"));    ImageView rdo=new ImageView(red);
        
        npp.setFitWidth(21); npp.setFitHeight(21);  opp.setFitWidth(21); opp.setFitHeight(21);
        svv.setFitWidth(21); svv.setFitHeight(21);  prr.setFitWidth(21); prr.setFitHeight(21);
        cll.setFitWidth(21); cll.setFitHeight(21);  bll.setFitWidth(21); bll.setFitHeight(21);
        cbb.setFitWidth(21); cbb.setFitHeight(21);  rnn.setFitWidth(21); rnn.setFitHeight(21);
        nfi.setFitHeight(21); nfi.setFitWidth(21);  un.setFitHeight(21); un.setFitWidth(21);
        rdo.setFitHeight(21); rdo.setFitWidth(21);
        
        //second flow panel
        Button npb=new Button(); npb.setStyle("-fx-background-color:lightsteelblue"); npb.setGraphic(npp);
        Button opb=new Button(); opb.setStyle("-fx-background-color:lightsteelblue"); opb.setGraphic(opp);
        Button svb=new Button(); svb.setStyle("-fx-background-color:lightsteelblue"); svb.setGraphic(svv);
        Button prb=new Button(); prb.setStyle("-fx-background-color:lightsteelblue"); prb.setGraphic(prr);
        Button clb=new Button(); clb.setStyle("-fx-background-color:lightsteelblue"); clb.setGraphic(cll);
        Button blb=new Button(); blb.setStyle("-fx-background-color:lightsteelblue"); blb.setGraphic(bll);
        Button cbbb=new Button(); cbbb.setStyle("-fx-background-color:lightsteelblue"); cbbb.setGraphic(cbb);
        Button rnb=new Button(); rnb.setStyle("-fx-background-color:lightsteelblue"); rnb.setGraphic(rnn);
        Button nflb=new Button(); nflb.setStyle("-fx-background-color:lightsteelblue"); nflb.setGraphic(nfi);
        Button undb=new Button(); undb.setStyle("-fx-background-color:lightsteelblue"); undb.setGraphic(un);
        Button redb=new Button(); redb.setStyle("-fx-background-color:lightsteelblue"); redb.setGraphic(rdo);
        
        //television
        Label tv=new Label(); tv.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure"); 
        GridPane tvp=new GridPane(); tvp.setPadding(new Insets(0,0,0,0)); tvp.setVgap(0); tvp.setHgap(0); 
        tvp.setAlignment(Pos.CENTER); tvp.setPrefWidth(181); tvp.setStyle("-fx-background-color:black"); tvp.add(tv,0,0);
        
        GridPane ndp=new GridPane(); ndp.setPadding(new Insets(3,3,3,3)); ndp.setVgap(1); ndp.setHgap(7);
        ndp.setAlignment(Pos.CENTER_LEFT); ndp.setStyle(bg); ndp.add(nflb,0,0); ndp.add(npb,1,0); 
        ndp.add(opb,2,0); ndp.add(svb,3,0); ndp.add(undb,4,0); ndp.add(redb,5,0);
        ndp.add(prb,6,0); ndp.add(clb,7,0); ndp.add(blb,8,0); ndp.add(cbbb,9,0); ndp.add(rnb,10,0); ndp.add(tvp,11,0);
        
        //projects view panel
        Label prj=new Label("Projects"); prj.setStyle("-fx-font:normal 19px 'Cambria'; -fx-text-fill:azure");
        ListView lvw=new ListView(); lvw.setStyle("-fx-control-inner-background:darkslateblue; -fx-font:normal 15px 'Cambria'");
        lvw.setPrefWidth(217); lvw.setPrefHeight(418); lvw.setItems(obls);
       
        //creating tv 2
        Label act=new Label(); act.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
        GridPane tv2=new GridPane(); tv2.setPadding(new Insets(7,7,7,7)); tv2.setVgap(7); tv2.setHgap(7);
        tv2.setAlignment(Pos.CENTER); tv2.setStyle("-fx-background-color:black"); tv2.add(act,0,0); tv2.setPrefWidth(198);
         
        FadeTransition fdd=new FadeTransition(Duration.seconds(3)); fdd.setCycleCount(1); fdd.setFromValue(1);
        fdd.setToValue(1); fdd.setAutoReverse(false); fdd.setNode(tv2);
        
        fdd.setOnFinished(ev->{act.setText("");});
        
        lvw.setCellFactory(lstv->{
            return new ListCell<File>() {
                @Override
                public void updateItem(File fi, boolean empty) {
                    super.updateItem(fi,empty);
                    setText(fi==null ? null : fi.getName());
                }
            };
        });
        
        GridPane prh=new GridPane(); prh.setPadding(new Insets(8,8,8,8)); prh.setVgap(7); prh.setHgap(7);
        prh.setStyle(bg); prh.setAlignment(Pos.TOP_CENTER); prh.add(prj,0,0); prh.add(lvw,0,2); prh.add(tv2,0,4);
        
        //editor & scenes panel
        Label scy=new Label("Scenes"); scy.setStyle("-fx-font:normal 19px 'Cambria'; -fx-text-fill:azure");
        ComboBox<String> combo=new ComboBox();
        combo.setStyle("-fx-control-inner-background:lightgrey;"
                + "-fx-font:normal 15px 'Cambria';-fx-background-color:lightsteelblue");
        combo.setPrefWidth(377);
        GridPane ed=new GridPane(); ed.setPadding(new Insets(3,3,3,3)); ed.setVgap(1); ed.setHgap(7);
        ed.setAlignment(Pos.CENTER_LEFT); ed.setStyle(bg); ed.add(scy,0,0); ed.add(combo,1,0);
        
        TextArea edt=new TextArea(); edt.setStyle("-fx-text-fill:darkorange; -fx-font:normal 16px 'monospaced';"
                + "-fx-control-inner-background:black");
        edt.setPrefWidth(878); edt.setPrefHeight(517);
        
        GridPane alo=new GridPane(); alo.setPadding(new Insets(3,3,3,3)); alo.setVgap(7); alo.setHgap(7);
        alo.setAlignment(Pos.CENTER_LEFT); alo.setStyle(bg); alo.add(ed,0,0); alo.add(edt,0,1); 
        
        GridPane lwr=new GridPane(); lwr.setPadding(new Insets(3,3,3,3)); lwr.setVgap(1); lwr.setHgap(7);
        lwr.setAlignment(Pos.TOP_LEFT); lwr.setStyle(bg); lwr.add(prh,0,0); lwr.add(alo,1,0);
        
        //Upper decoration
        ImageView icn=new ImageView(icc); icn.setFitWidth(31); icn.setFitHeight(31);
        GridPane lft=new GridPane(); lft.setVgap(7); lft.setHgap(7); lft.setPadding(new Insets(3,3,3,3));
        lft.setAlignment(Pos.CENTER_LEFT); lft.setStyle("-fx-background-color:black"); lft.add(icn,0,0);
        
        Label ttl=new Label("SeumxDev"); ttl.setStyle("-fx-font:normal 23px 'Cambria'; -fx-text-fill:azure");
        GridPane ctr=new GridPane(); ctr.setVgap(0); ctr.setHgap(0); ctr.setPadding(new Insets(3,3,3,3));
        ctr.setAlignment(Pos.CENTER); ctr.setStyle("-fx-background-color:black"); ctr.add(ttl,0,0); 
        
        Button min=new Button("_"); Button clz=new Button("X");
        min.setStyle("-fx-font:normal 18px 'Cambria'; -fx-background-color:lightsteelblue");
        clz.setStyle("-fx-font:normal 18px 'Cambria'; -fx-background-color:lightsteelblue");
        min.setOnMouseMoved(ev->{
            min.setStyle("-fx-font:normal 18px 'Cambria'; -fx-background-color:deeppink");
        });
        clz.setOnMouseMoved(ev->{
            clz.setStyle("-fx-font:normal 18px 'Cambria'; -fx-background-color:deeppink");
        });
        
        min.setOnMouseExited(ev->{
            min.setStyle("-fx-font:normal 18px 'Cambria'; -fx-background-color:lightsteelblue");
        });
        clz.setOnMouseExited(ev->{
            clz.setStyle("-fx-font:normal 18px 'Cambria'; -fx-background-color:lightsteelblue");
        });
        
        min.setOnAction(ev->{
            Stage.setIconified(true);
        });
        
        GridPane bpn=new GridPane(); bpn.setVgap(0); bpn.setHgap(4); bpn.setPadding(new Insets(3,3,3,3));
        bpn.setAlignment(Pos.CENTER_RIGHT); bpn.setStyle("-fx-background-color:black"); bpn.add(min,0,0); bpn.add(clz,1,0);
        
        BorderPane tpp=new BorderPane(); tpp.setPadding(new Insets(0,0,0,0)); tpp.setLeft(lft); tpp.setCenter(ctr); 
        tpp.setRight(bpn);
        
        tpp.setOnMousePressed(ev->{
            xoff=ev.getSceneX(); yoff=ev.getSceneY();
        });
        tpp.setOnMouseDragged(er->{
            Stage.setX(er.getScreenX()-xoff); Stage.setY(er.getScreenY()-yoff);
        });
        
        GridPane pn=new GridPane(); pn.setVgap(1); pn.setHgap(1); pn.setPadding(new Insets(1,1,1,1)); pn.setAlignment(Pos.TOP_LEFT);
        pn.setStyle(bg); pn.setOpacity(1); pn.add(bar,0,0); pn.add(ndp,0,1); pn.add(lwr,0,2);
        
        BorderPane brd=new BorderPane(); brd.setPadding(new Insets(0,0,0,0)); brd.setBackground(big);
        brd.setCenter(pn); brd.setTop(tpp);
        
        Scene sn=new Scene(brd); Stage.setTitle("SeumxDev"); Stage.setScene(sn); 
        
        //primary Actions
        npb.setOnMouseMoved(ev->{
            npb.setStyle("-fx-background-color:plum");  tv.setText("New Project");
        });
        npb.setOnMouseExited(ev->{
            npb.setStyle("-fx-background-color:lightsteelblue");  tv.setText("");
        });
        
        opb.setOnMouseMoved(ev->{
            opb.setStyle("-fx-background-color:plum");  tv.setText("Open Project");
        });
        opb.setOnMouseExited(ev->{
            opb.setStyle("-fx-background-color:lightsteelblue");  tv.setText("");
        });
        
        svb.setOnMouseMoved(ev->{
            svb.setStyle("-fx-background-color:plum");  tv.setText("Save");
        });
        svb.setOnMouseExited(ev->{
            svb.setStyle("-fx-background-color:lightsteelblue");  tv.setText("");
        });
        
        prb.setOnMouseMoved(ev->{
            prb.setStyle("-fx-background-color:plum");  tv.setText("Project Properties");
        });
        prb.setOnMouseExited(ev->{
            prb.setStyle("-fx-background-color:lightsteelblue");  tv.setText("");
        });
        
        clb.setOnMouseMoved(ev->{
            clb.setStyle("-fx-background-color:plum");  tv.setText("Close Project");
        });
        clb.setOnMouseExited(ev->{
            clb.setStyle("-fx-background-color:lightsteelblue");  tv.setText("");
        });
        
        blb.setOnMouseMoved(ev->{
            blb.setStyle("-fx-background-color:plum");  tv.setText("Build Project");
        });
        blb.setOnMouseExited(ev->{
            blb.setStyle("-fx-background-color:lightsteelblue");  tv.setText("");
        });
        
        cbbb.setOnMouseMoved(ev->{
            cbbb.setStyle("-fx-background-color:plum");  tv.setText("Clean & Build Project");
        });
        cbbb.setOnMouseExited(ev->{
            cbbb.setStyle("-fx-background-color:lightsteelblue");  tv.setText("");
        });
        
        rnb.setOnMouseMoved(ev->{
            rnb.setStyle("-fx-background-color:plum");  tv.setText("Run Project");
        });
        rnb.setOnMouseExited(ev->{
            rnb.setStyle("-fx-background-color:lightsteelblue");  tv.setText("");
        });
        
        nflb.setOnMouseMoved(ev->{
            nflb.setStyle("-fx-background-color:plum");  tv.setText("New File");
        });
        nflb.setOnMouseExited(ev->{
            nflb.setStyle("-fx-background-color:lightsteelblue");  tv.setText("");
        });
        
        undb.setOnMouseMoved(ev->{
            undb.setStyle("-fx-background-color:plum");  tv.setText("Undo");
        });
        undb.setOnMouseExited(ev->{
            undb.setStyle("-fx-background-color:lightsteelblue");  tv.setText("");
        });
        
        redb.setOnMouseMoved(ev->{
            redb.setStyle("-fx-background-color:plum");  tv.setText("Redo");
        });
        redb.setOnMouseExited(ev->{
            redb.setStyle("-fx-background-color:lightsteelblue");  tv.setText("");
        });
        
        //Start animation
        ImageView vw=new ImageView(icc); vw.setFitWidth(237); vw.setFitHeight(237); 
        Label smx=new Label("SeumxDev"); Label sx=new Label("Develop Desktop Apps With Html & JavaScript");
        smx.setStyle("-fx-font:bold 29px 'Cambria'; -fx-text-fill:azure;");
        sx.setStyle("-fx-font:normal 18px 'Cambria'; -fx-text-fill:lightsteelblue");
        
        ProgressIndicator ind=new ProgressIndicator(); ind.setStyle("-fx-progress-color:white"); 
        ind.setPrefSize(27, 27); 
        
        GridPane ju=new GridPane(); ju.setPadding(new Insets(2,2,2,2)); ju.setVgap(4); ju.setHgap(49);
        ju.setAlignment(Pos.CENTER_RIGHT); ju.setStyle("-fx-background-color:black"); ju.add(smx,0,0); 
        ju.add(sx,0,1); ju.add(ind,1,1);
        
        GridPane ji=new GridPane(); ji.setPadding(new Insets(2,2,2,2)); ji.setVgap(4); ji.setHgap(4);
        ji.setAlignment(Pos.CENTER); ji.setStyle("-fx-background-color:black"); ji.add(vw,0,0);
        
        BorderPane bd=new BorderPane(); bd.setCenter(ji); bd.setBottom(ju); 
        Stage srt=new Stage(); srt.setHeight(378); srt.setWidth(597); srt.initStyle(StageStyle.UNDECORATED);
        Scene sf=new Scene(bd); srt.setScene(sf); srt.getIcons().add(icc);  srt.setTitle("SMXD"); srt.show();
        
        //transition
        FadeTransition fd=new FadeTransition(Duration.seconds(18)); fd.setNode(vw); fd.setCycleCount(1); fd.setAutoReverse(false); 
        fd.setFromValue(0); fd.setToValue(1); fd.play();
        
        fd.setOnFinished(ev->{
            srt.close(); Stage.show();
        });
        
        //Secondary Actions
        lt.setOnAction(ev-> {
            ndp.setStyle("-fx-background-color:navy"); prh.setStyle("-fx-background-color:navy");
            ed.setStyle("-fx-background-color:navy"); alo.setStyle("-fx-background-color:navy");
            lwr.setStyle("-fx-background-color:navy");pn.setStyle("-fx-background-color:navy");
            act.setText("Need to Restart App"); fdd.play();
            
            //saving theme
            try {
            prp.setProperty("theme","navy");
            FileOutputStream ot=new FileOutputStream("Data.bat");
            ObjectOutputStream objo=new ObjectOutputStream(ot);
            objo.writeObject(prp); 
            
            } catch (IOException ex) {
                System.out.println("IO Error...");
            }
        });
        
        gry.setOnAction(ev->{
            ndp.setStyle("-fx-background-color:grey"); prh.setStyle("-fx-background-color:grey");
            ed.setStyle("-fx-background-color:grey"); alo.setStyle("-fx-background-color:grey");
            lwr.setStyle("-fx-background-color:grey"); pn.setStyle("-fx-background-color:grey");
            act.setText("Need to Restart App"); fdd.play();
            
            //save theme
            try {
                prp.setProperty("theme","grey");
                FileOutputStream ot=new FileOutputStream("Data.bat");
                ObjectOutputStream objo=new ObjectOutputStream(ot);
                objo.writeObject(prp);
                
            } catch (IOException ex) {
                System.out.println("IO Error...");
            }
        });
        
        grn.setOnAction(ev->{
            ndp.setStyle("-fx-background-color:darkgreen"); prh.setStyle("-fx-background-color:darkgreen");
            ed.setStyle("-fx-background-color:darkgreen"); alo.setStyle("-fx-background-color:darkgreen");
            lwr.setStyle("-fx-background-color:darkgreen"); pn.setStyle("-fx-background-color:darkgreen");
            act.setText("Need to Restart App"); fdd.play();
            
            //save theme
            try {
                prp.setProperty("theme","darkgreen");
                FileOutputStream ot=new FileOutputStream("Data.bat");
                ObjectOutputStream objo=new ObjectOutputStream(ot);
                objo.writeObject(prp);
                
            } catch (IOException ex) {
                System.out.println("IO Error...");
            }
        });
        
        rd.setOnAction(ev->{
            ndp.setStyle("-fx-background-color:darkred"); prh.setStyle("-fx-background-color:darkred");
            ed.setStyle("-fx-background-color:darkred"); alo.setStyle("-fx-background-color:darkred");
            lwr.setStyle("-fx-background-color:darkred"); pn.setStyle("-fx-background-color:darkred");
            act.setText("Need to Restart App"); fdd.play();
            
            //save theme
            try {
                prp.setProperty("theme","darkred");
                FileOutputStream ot=new FileOutputStream("Data.bat");
                ObjectOutputStream objo=new ObjectOutputStream(ot);
                objo.writeObject(prp);
                
            } catch (IOException ex) {
                System.out.println("IO Error...");
            }
        });
        
        prl.setOnAction(ev->{
            ndp.setStyle("-fx-background-color:purple"); prh.setStyle("-fx-background-color:purple");
            ed.setStyle("-fx-background-color:purple"); alo.setStyle("-fx-background-color:purple");
            lwr.setStyle("-fx-background-color:purple"); pn.setStyle("-fx-background-color:purple");
            act.setText("Need to Restart App"); fdd.play();
            
            //save theme
            try {
                prp.setProperty("theme","purple");
                FileOutputStream ot=new FileOutputStream("Data.bat");
                ObjectOutputStream objo=new ObjectOutputStream(ot);
                objo.writeObject(prp);
                
            } catch (IOException ex) {
                System.out.println("IO Error...");
            }
        });
        
        cyn.setOnAction(ev->{
            ndp.setStyle("-fx-background-color:darkcyan"); prh.setStyle("-fx-background-color:darkcyan"); 
            ed.setStyle("-fx-background-color:darkcyan"); alo.setStyle("-fx-background-color:darkcyan"); 
            lwr.setStyle("-fx-background-color:darkcyan"); pn.setStyle("-fx-background-color:darkcyan"); 
            act.setText("Need to Restart App"); fdd.play();
            
            //save theme
            try {
                prp.setProperty("theme","darkcyan");
                FileOutputStream ot=new FileOutputStream("Data.bat");
                ObjectOutputStream objo=new ObjectOutputStream(ot);
                objo.writeObject(prp);
                
            } catch (IOException ex) {
                System.out.println("IO Error...");
            }
        });
        
        pnk.setOnAction(ev->{
            ndp.setStyle("-fx-background-color:deeppink"); prh.setStyle("-fx-background-color:deeppink");
            ed.setStyle("-fx-background-color:deeppink"); alo.setStyle("-fx-background-color:deeppink");
            lwr.setStyle("-fx-background-color:deeppink"); pn.setStyle("-fx-background-color:deeppink");
            act.setText("Need to Restart App"); fdd.play();
            
            //save theme
            try {
                prp.setProperty("theme","deeppink");
                FileOutputStream ot=new FileOutputStream("Data.bat");
                ObjectOutputStream objo=new ObjectOutputStream(ot);
                objo.writeObject(prp);
                
            } catch (IOException ex) {
                System.out.println("IO Error...");
            }
        });
        
        gld.setOnAction(ev->{
            ndp.setStyle("-fx-background-color:darkgoldenrod"); prh.setStyle("-fx-background-color:darkgoldenrod");
            ed.setStyle("-fx-background-color:darkgoldenrod"); alo.setStyle("-fx-background-color:darkgoldenrod");
            lwr.setStyle("-fx-background-color:darkgoldenrod"); pn.setStyle("-fx-background-color:darkgoldenrod");
            act.setText("Need to Restart App"); fdd.play();
            
            //save theme
            try {
                prp.setProperty("theme","darkgoldenrod");
                FileOutputStream ot=new FileOutputStream("Data.bat");
                ObjectOutputStream objo=new ObjectOutputStream(ot);
                objo.writeObject(prp);
                
            } catch (IOException ex) {
                System.out.println("IO Error...");
            }
        });
        
        nf.setOnAction(ev->{ if(curr!=null) {  if(cs!=null) {cs.close();}
            //top
            Label u=new Label("New File"); u.setStyle("-fx-font:normal 19px 'Cambria'; -fx-text-fill:azure");
            GridPane up=new GridPane(); up.setPadding(new Insets(5,5,5,5)); up.setVgap(3); up.setHgap(3);
            up.setAlignment(Pos.CENTER); up.setStyle("-fx-background-color:black"); up.add(u,0,0);
            
            //mdle
            Label fn=new Label("File Name"); fn.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            Label prf=new Label("Project"); prf.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            Label fn2=new Label(curr); fn2.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            TextField tf=new TextField(); tf.setStyle("-fx-font:normal 15px 'Cambria'");
            
            GridPane ffp=new GridPane(); ffp.setPadding(new Insets(8,8,8,8)); ffp.setVgap(14); ffp.setHgap(7);
            ffp.setAlignment(Pos.CENTER); ffp.setStyle(bg); ffp.add(fn,0,0); ffp.add(tf,1,0); ffp.add(prf,0,1); ffp.add(fn2,1,1);
            
            //btm
            Label ex=new Label(); ex.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            Button crt=new Button("Create"); Button clc=new Button("Cancel");
            crt.setStyle("-fx-background-color:black; -fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            clc.setStyle("-fx-background-color:black; -fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            clc.setPrefSize(87,31); crt.setPrefSize(87,31);
            
            GridPane up2=new GridPane(); up2.setPadding(new Insets(5,5,5,5)); up2.setVgap(3); up2.setHgap(9);
            up2.setAlignment(Pos.CENTER_RIGHT); up2.setStyle(bg); 
            up2.add(ex,0,0); up2.add(crt,1,0); up2.add(clc,2,0);
            
            BorderPane d=new BorderPane(); d.setPadding(new Insets(0,0,0,0)); d.setTop(up); d.setCenter(ffp); 
            d.setBottom(up2);
            
            cs=new Stage(); cs.setWidth(589); cs.setHeight(347); cs.initStyle(StageStyle.UNDECORATED);
            cs.setScene(new Scene(d)); cs.initOwner(Stage); cs.show();
            
            up.setOnMousePressed(ui->{
                xoff=ui.getSceneX(); yoff=ui.getSceneY();
            });
            up.setOnMouseDragged(r->{
                cs.setX(r.getScreenX()-xoff); cs.setY(r.getScreenY()-yoff);
            });
            
            clc.setOnAction(er->{
                cs.close();
            });
            
            crt.setOnAction(eb->{   String hf="";
                //read temp
                try {
                    DataInputStream din=new DataInputStream(new FileInputStream("BuildTools/template.tmp"));
                    while (din.available()>0) {
                        hf=hf+din.readLine()+"\n";
                    }
                    
                    //checking for filename avalb
                    File g=new File(curr+"/Scene"); String [] cbc1=g.list(); List joi=new ArrayList();
                    joi.addAll(Arrays.asList(cbc1));
                    
                    if(!joi.contains(tf.getText()+".html")) {
                        ex.setText("");
                        
                        File h=new File(curr+"/Scene/"+tf.getText()+".html");
                        FileWriter wrt=new FileWriter(h);
                        wrt.write(hf); wrt.flush(); wrt.close();
                        
                        //updating cb
                        cbc1=g.list(); ObservableList<String> ob=FXCollections.observableArrayList(cbc1);
                        combo.setItems(ob); cs.close();
                        
                    } else {
                        ex.setText("Filename Already exists");
                    }
                    
                } catch (IOException eix) {
                    System.out.println("tmp rd error...");
                }
            });
            
        } else {
            if(cs!=null) { cs.close(); }
            Label h=new Label("No Project Is Open"); h.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            Button v=new Button("Close"); v.setPrefSize(187,31);
            v.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(27); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(h,0,0); gf.add(v,0,1);
            
            cs=new Stage(); cs.setWidth(274); cs.setHeight(157); cs.initStyle(StageStyle.UNDECORATED);
            cs.setScene(new Scene(gf)); cs.initOwner(Stage); cs.show();
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                cs.setX(er.getScreenX()-xoff); cs.setY(er.getScreenY()-yoff);
            });
            v.setOnAction(tr->{
                cs.close();
            });
        }
            
        });
        
        np.setOnAction(ev->{ if(cs!=null) { cs.close(); }
            Label crt=new Label("Create New Project"); Label prn=new Label("Project Name");
            Label prol=new Label("Project Location"); Label prolv=new Label(prdir+""); TextField prnf=new TextField(); 
            Button crtp=new Button("Create"); Button clsi=new Button("Close"); Label ext=new Label();
            
            //Styling my nodes
            crt.setStyle("-fx-font:normal 19px 'Cambria'; -fx-text-fill:azure;");
            prn.setStyle("-fx-font:normal 18px 'Cambria'; -fx-text-fill:azure;");
            prol.setStyle("-fx-font:normal 18px 'Cambria'; -fx-text-fill:azure;");
            prolv.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:lightsteelblue;");
            prnf.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:darkgreen;");
            crtp.setStyle("-fx-background-color:black;-fx-text-fill:azure;-fx-font:normal 18px 'Cambria'");
            clsi.setStyle("-fx-background-color:black;-fx-text-fill:azure;-fx-font:normal 18px 'Cambria'");
            ext.setStyle("-fx-font:bold 16px 'Cambria'; -fx-text-fill:black;");
            
            crtp.setPrefSize(118,37); clsi.setPrefSize(118,37);
            
            GridPane upn=new GridPane(); upn.setPadding(new Insets(8,8,8,8)); upn.setVgap(18); upn.setHgap(13);
            upn.setAlignment(Pos.CENTER); upn.setStyle(bg); upn.add(prn,0,0); upn.add(prnf,1,0);
            upn.add(prol,0,1); upn.add(prolv,1,1); upn.add(ext,1,4);
            
            GridPane hdr=new GridPane(); hdr.setPadding(new Insets(5,5,5,5)); hdr.setStyle("-fx-background-color:black");
            hdr.setAlignment(Pos.CENTER); hdr.add(crt,0,0); 
            
            GridPane lwe=new GridPane(); lwe.setAlignment(Pos.CENTER_RIGHT); lwe.setPadding(new Insets(9,9,9,9));
            lwe.setHgap(14); lwe.setStyle(bg);
            lwe.add(crtp,0,0); lwe.add(clsi,1,0);
            
            BorderPane bdr=new BorderPane(); bdr.setPadding(new Insets(0,0,0,0)); bdr.setTop(hdr); bdr.setCenter(upn);
            bdr.setBottom(lwe);
            
            cs=new Stage(); cs.setWidth(589); cs.setHeight(347); cs.initStyle(StageStyle.UNDECORATED);
            Scene hg=new Scene(bdr); cs.setScene(hg); cs.initOwner(Stage); cs.show();
            
            clsi.setOnAction(evv->{
                cs.close();
            });
            
            hdr.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            
            hdr.setOnMouseDragged(evr->{
                cs.setX(evr.getScreenX()-xoff); cs.setY(evr.getScreenY()-yoff);
            });
            
            prnf.setOnKeyReleased(eri->{
                prolv.setText(prdir+prnf.getText());
            });
            
            crtp.setOnAction(yu->{
                //load project file location
                fll=new File(prdir+prnf.getText()); 
                if(!fll.exists()) { fll.mkdirs();  
                File sci=new File(prdir+prnf.getText()+"/Scene");
                if(!sci.exists()) { sci.mkdirs(); }
                
                //create project files
                File bldt=new File("BuildTools/compiler.bin");
                File bldd=new File("BuildTools/AppData.dll");
                File bldi=new File("BuildTools/icon.png");
                
                File ap=new File(prdir+prnf.getText()+"/"+prnf.getText()+".exe");
                File dt=new File(prdir+prnf.getText()+"/"+"AppData.dll");
                File ic=new File(prdir+prnf.getText()+"/"+"icon.png");
                
                //copying files
                try {
                    copyFile(bldt, ap);
                    copyFile(bldd,dt);
                    copyFile(bldi,ic);
                    
                    //creating HTML template
                    File tpl=new File("BuildTools/template.tmp"); String rdt="";
                    DataInputStream inda=new DataInputStream(new FileInputStream(tpl.getAbsolutePath()));
                    while (inda.available()>0) {
                        rdt=rdt+inda.readLine()+"\n";
                    }
                    
                    File idx=new File(prdir+prnf.getText()+"/Scene/index.html");
                    FileWriter wrt=new FileWriter(idx);
                    wrt.write(rdt); wrt.flush(); wrt.close();
                    
                    //applying data
                    combo.setValue("index.html"); edt.setText(rdt); cs.close(); kih="index.html";
                    
                    //adding to prjcts menu and curr
                    curr=prdir+prnf.getText(); System.out.println("Curr:"+curr);
                    
                    lst.add(new File(prdir+prnf.getText())); obls.clear(); obls.addAll(lst); lvw.setItems(obls);
                    
                    cbc=sci.list(); List<String> uip=new ArrayList();
                    uip.addAll(Arrays.asList(cbc));
                    
                    ObservableList on=FXCollections.observableArrayList(uip); combo.setItems(on);
                    
                    //writing lst
                    FileOutputStream outy=new FileOutputStream("proj.dll");
                    ObjectOutputStream objy=new ObjectOutputStream(outy);
                    objy.writeObject(lst); objy.flush(); objy.close(); ext.setText("");
                    
                } catch (IOException ex) {
                    System.out.println(ex.getCause());
                }
                }  else {
                    ext.setText("Project Name Already Exists.");
                }
            });
            
        });
        
        lvw.setOnMouseClicked(ev->{ kih=null;
            
            String file=lvw.getSelectionModel().getSelectedItem().toString();
            //Save editing project First
            if(curr!=null && kih!=null){ 
                try {
                    FileWriter wrt=new FileWriter(new File(curr+"/Scene/"+combo.getValue()));
                    wrt.write(edt.getText()); wrt.flush(); wrt.close(); fdd.play();
                } catch (IOException ex) {
                    System.out.println("pr op error...");
                }
            }
            
            if(ev.getClickCount()==1) { kih=null;
                curr=file; //System.out.println(file);
                cbc=new File(file+"/Scene").list(); List hgl=new ArrayList();
                hgl.addAll(Arrays.asList(cbc)); ObservableList on=FXCollections.observableArrayList(hgl); 
                combo.setItems(on); act.setText("Project Saved");
                act.setText("Opening Project"); fdd.play();
                
                combo.setValue(null); edt.setText(null);
            }
        });
        
        combo.setOnAction(ev->{
            
            try {
                if (kih!=null && combo.getValue()!=null) {
                //Saving Document
                File sav=new File(curr+"/Scene/"+kih);
                FileWriter wrt=new FileWriter(sav);
                wrt.write(edt.getText()); wrt.flush(); wrt.close(); 
                
                act.setText(kih+"Saved"); fdd.play();  } kih=combo.getValue();  
                
                //getting selected file data to edt
                DataInputStream din=new DataInputStream(new FileInputStream(curr+"/Scene/"+kih));
                String hl="";
                while(din.available()>0) {
                    hl=hl+din.readLine()+"\n";
                } edt.setText(hl);
                
            } catch (IOException ex){
                System.out.println("Combo save error...");
            }
            
        });
        
        clz.setOnAction(ev->{ String df="",dd; 
        try {
            File gy=new File(curr+"/Scene/"+combo.getValue());
            DataInputStream din=new DataInputStream(new FileInputStream(gy));
            while(din.available()>0) {
                df=df+din.readLine()+"\n";
            } dd=edt.getText();
            
            if(df.equals(dd)) {
                Stage.close();
            } else {                 if(cs!=null) {cs.close(); }
                
                //top
                Label lb=new Label("Save Changes?"); lb.setStyle("-fx-font:normal 19px 'Cambria'; -fx-text-fill:azure");
                GridPane oi=new GridPane(); oi.setPadding(new Insets(7,7,7,7)); oi.setVgap(4); oi.setHgap(4);
                oi.setAlignment(Pos.CENTER); oi.setStyle("-fx-background-color:black"); oi.add(lb,0,0);
                
                //center
                Label msg=new Label("Scene Document Changes Are Not Saved.\nDo you want to save these changes before exiting?");
                msg.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
                GridPane io=new GridPane(); io.setPadding(new Insets(7,7,7,7)); io.setVgap(4); io.setHgap(4);
                io.setAlignment(Pos.CENTER); io.setStyle(bg); io.add(msg,0,0);
                
                //btm
                Button ys=new Button("Save"); Button no=new Button("Discard"); Button ccl=new Button("Cancel");
                ys.setStyle("-fx-background-color:black; -fx-text-fill:azure; -fx-font:normal 16px 'Cambria'");
                no.setStyle("-fx-background-color:black; -fx-text-fill:azure; -fx-font:normal 16px 'Cambria'");
                ccl.setStyle("-fx-background-color:black; -fx-text-fill:azure; -fx-font:normal 16px 'Cambria'");
                ys.setPrefSize(178, 37); no.setPrefSize(178, 37); ccl.setPrefSize(178, 37);
                GridPane oii=new GridPane(); oii.setPadding(new Insets(9,9,9,9)); oii.setVgap(4); oii.setHgap(17);
                oii.setAlignment(Pos.CENTER); oii.setStyle(bg); oii.add(ys,0,0); oii.add(no,1,0); oii.add(ccl,2,0);
                
                BorderPane bdr=new BorderPane(); bdr.setPadding(new Insets(0,0,0,0)); bdr.setTop(oi);
                bdr.setCenter(io); bdr.setBottom(oii); 
                Scene scu=new Scene(bdr); 
                cs=new Stage(); cs.setWidth(589); cs.setHeight(347); cs.initStyle(StageStyle.UNDECORATED);
                cs.setScene(scu); cs.initOwner(Stage); cs.show();
                
                ys.setOnAction(er->{
                    try {
                        FileWriter wrt=new FileWriter(gy);
                        wrt.write(dd); wrt.flush(); wrt.close(); Stage.close();
                    } catch (IOException ex){
                        System.out.println("Save cls error....");
                    }
                });
                
                no.setOnAction(er->{
                    Stage.close();
                });
                
                ccl.setOnAction(er->{
                    cs.close();
                });
                
                oi.setOnMousePressed(er->{
                    xoff=er.getSceneX(); yoff=er.getSceneY();
                });
                oi.setOnMouseDragged(er->{
                    cs.setX(er.getScreenX()-xoff); cs.setY(er.getScreenY()-yoff);
                });
            }
            
        } catch (IOException ex){
            System.out.println("close save error....");
        }
        
        if(combo.getValue()==null) {
            Stage.close();
        }
        });
        
        op.setOnAction(ev->{         if(cs!=null) {cs.close();}
            //top dlg
            Label ope=new Label("Open Project"); ope.setStyle("-fx-font:normal 19px 'Cambria'; -fx-text-fill:azure");
            GridPane oe=new GridPane(); oe.setPadding(new Insets(7,7,7,7)); oe.setVgap(4); oe.setHgap(4);
            oe.setAlignment(Pos.CENTER); oe.setStyle("-fx-background-color:black"); oe.add(ope,0,0);
            
            //contect
            Label ope1=new Label(prdir); ope1.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            File lvd=new File(prdir); cbc=lvd.list(); List fy=new ArrayList(); 
            fy.addAll(Arrays.asList(cbc)); ObservableList obn=FXCollections.observableArrayList(fy);
            ListView lvm=new ListView(); lvm.setItems(obn); lvm.setPrefSize(478,248); 
            lvm.setStyle("-fx-control-inner-background:black; -fx-font:normal 15px 'Cambria'");
            
            GridPane oe1=new GridPane(); oe1.setPadding(new Insets(2,2,2,2)); oe1.setVgap(7); oe1.setHgap(8);
            oe1.setAlignment(Pos.CENTER); oe1.setStyle(bg); oe1.add(ope1,0,0); oe1.add(lvm,0,1); 
            
            //btns btm
            Button opnn=new Button("Open"); Button clc=new Button("Cancel"); Label yui=new Label();
            opnn.setStyle("-fx-background-color:black;-fx-text-fill:azure;-fx-font:normal 18px 'Cambria'");
            clc.setStyle("-fx-background-color:black;-fx-text-fill:azure;-fx-font:normal 18px 'Cambria'");
            yui.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            clc.setPrefSize(87,34); opnn.setPrefSize(87,34);
            
            GridPane oe2=new GridPane(); oe2.setPadding(new Insets(3,3,3,3)); oe2.setVgap(3); oe2.setHgap(4);
            oe2.setAlignment(Pos.CENTER_RIGHT); oe2.setStyle(bg); oe2.add(yui,0,0); oe2.add(opnn,1,0); oe2.add(clc,2,0); 
            
            BorderPane bpo=new BorderPane(); bpo.setPadding(new Insets(0,0,0,0)); bpo.setTop(oe); bpo.setCenter(oe1);
            bpo.setBottom(oe2);
            
            cs=new Stage(); cs.setWidth(587); cs.setHeight(357); cs.initStyle(StageStyle.UNDECORATED);
            Scene hg=new Scene(bpo); cs.setScene(hg); cs.initOwner(Stage); cs.show();
            
            oe.setOnMousePressed(evt->{
                xoff=evt.getSceneX(); yoff=evt.getSceneY();
            });
            oe.setOnMouseDragged(ok->{
                cs.setX(ok.getScreenX()-xoff); cs.setY(ok.getScreenY()-yoff);
            });
            
            clc.setOnAction(rt->{
                cs.close();
            });
            
            opnn.setOnAction(tc->{
                if(lvm.getSelectionModel().getSelectedItem()!=null) {
                    String lkj=lvm.getSelectionModel().getSelectedItem().toString();
                    File eds=new File(prdir+lkj);
                    String[] hs=eds.list(); List<String> km=new ArrayList();
                    km.addAll(Arrays.asList(hs));
                    
                    if(km.contains("AppData.dll") && km.contains(lkj+".exe") && km.contains("Scene") && km.contains("icon.png")){
                        
                        if(!lst.contains(new File(prdir+"/"+lkj))) {
                            lst.add(eds);
                            try {
                            FileOutputStream oju=new FileOutputStream("proj.dll");
                            ObjectOutputStream ih=new ObjectOutputStream(oju);
                            ih.writeObject(lst); ih.flush(); ih.close();  
                            
                            obls.clear(); obls.addAll(lst); lvw.setItems(obls); cs.close();
                            
                            } catch (IOException ex) {
                                System.out.println("wrt error");
                            }
                        } else {
                            yui.setText("Project Is Already Open!"); cs.close();
                        }
                        
                    } else {
                        yui.setText("The Selected Project Is Corrupt!");
                    }
                    
                } else {
                    yui.setText("Please Select Project!");
                }
            });
            
        });
        
        pp.setOnAction(ev->{    if(curr!=null) {  if(cs!=null) { cs.close(); }
            
            
            //top
            Label j=new Label("Project Properties"); j.setStyle("-fx-font:normal 19px 'Cambria'; -fx-text-fill:azure");
            GridPane k=new GridPane(); k.setPadding(new Insets(5,5,5,5)); k.setVgap(0); k.setHgap(0);
            k.setStyle("-fx-background-color:black"); k.setAlignment(Pos.CENTER); k.add(j,0,0);
            
            //reading pr data
            Properties r=new Properties();
            try {
            File p=new File(curr+"/AppData.dll"); 
            FileInputStream in =new FileInputStream(p);
            ObjectInputStream obji=new ObjectInputStream(in);
            r=(Properties)obji.readObject();
            
            } catch (IOException | ClassNotFoundException er) {
                System.out.println("Error reading File");
            }
            
            //content
            Label vis=new Label("Visual"); vis.setStyle("-fx-font:bold 17px 'Cambria'; -fx-text-fill:white");
            
            Label dec=new Label("Window Decoration"); Label tit=new Label("Window Title"); 
            Label opct=new Label("Window Opacity"); Label cn=new Label("Window Icon"); 
            Label rsz=new Label("Window Resizing");
            
            ComboBox cbx=new ComboBox(); ObservableList vx=FXCollections.observableArrayList("Default","SeumxStyle");
            cbx.setItems(vx); cbx.setValue(r.getProperty("Sdecr"));
            TextField titf=new TextField(r.getProperty("Stitle")); TextField icnf=new TextField(r.getProperty("Sicon"));
             ChoiceBox ctyf=new ChoiceBox(); ctyf.setValue(r.getProperty("Spcty"));
            ObservableList ux=FXCollections.observableArrayList("0.1","0.2","0.3","0.4","0.5","0.6","0.7","0.8","0.9","1");
            ctyf.setItems(ux); Button brt=new Button("..."); 
            brt.setStyle("-fx-font:normal 15px 'Cambria'; -fx-text-fill:navy; -fx-background-color:aliceblue");
            ChoiceBox rszf=new ChoiceBox(); rszf.setValue(r.getProperty("Srsz"));
            ObservableList t=FXCollections.observableArrayList("true","false"); rszf.setItems(t);
            
            dec.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            tit.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            opct.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            cn.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            rsz.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            
            cbx.setStyle("-fx-font:normal 15px 'Cambria'"); titf.setStyle("-fx-font:normal 15px 'Cambria'");
            icnf.setStyle("-fx-font:normal 15px 'Cambria'"); ctyf.setStyle("-fx-font:normal 15px 'Cambria'");
            rszf.setStyle("-fx-font:normal 15px 'Cambria'");
            
            GridPane kx=new GridPane(); kx.setPadding(new Insets(4,4,4,4)); kx.setVgap(5); kx.setHgap(8);
            kx.setAlignment(Pos.CENTER); kx.setStyle("-fx-background-color:black");
            kx.add(vis,0,0); kx.add(dec,0,2); kx.add(cbx,1,2); kx.add(tit,0,3); kx.add(titf,1,3); kx.add(opct,0,4);
            kx.add(ctyf,1,4); kx.add(cn,0,5); kx.add(icnf,1,5); kx.add(brt,2,5); kx.add(rsz,0,6); kx.add(rszf,1,6);
            
            Label i=new Label("Size"); 
            Label w=new Label("Width"); Label h=new Label("Height"); Label mw=new Label("Minimum Width");
            Label mh=new Label("Minimum Height"); Label mxw=new Label("Max Width"); Label mxh=new Label("Max Height");
            TextField wf=new TextField(r.getProperty("Swidth")); TextField hf=new TextField(r.getProperty("Sheight"));
            TextField mwf=new TextField(r.getProperty("minW")); TextField mhf=new TextField(r.getProperty("minH"));
            TextField mxwf=new TextField(r.getProperty("maxW")); TextField mxhf=new TextField(r.getProperty("maxH"));
            
            i.setStyle("-fx-font:bold 17px 'Cambria'; -fx-text-fill:white");
            w.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            h.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            mw.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            mh.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            mxw.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            mxh.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            
            wf.setStyle("-fx-font:normal 15px 'Cambria'"); hf.setStyle("-fx-font:normal 15px 'Cambria'");
            mwf.setStyle("-fx-font:normal 15px 'Cambria'"); mhf.setStyle("-fx-font:normal 15px 'Cambria'");
            mxwf.setStyle("-fx-font:normal 15px 'Cambria'"); mxhf.setStyle("-fx-font:normal 15px 'Cambria'");
            
            GridPane g=new GridPane(); g.setPadding(new Insets(7,7,7,7)); g.setVgap(5); g.setHgap(8); 
            g.setAlignment(Pos.CENTER); g.setStyle("-fx-background-color:black");
            g.add(i,0,0); g.add(w,0,2); g.add(wf,1,2); g.add(h,2,2); g.add(hf,3,2);
            g.add(mw,0,3); g.add(mwf,1,3); g.add(mh,2,3); g.add(mhf,3,3);
            g.add(mxw,0,4); g.add(mxwf,1,4); g.add(mxh,2,4); g.add(mxhf,3,4);
            
            GridPane mdl=new GridPane(); mdl.setPadding(new Insets(0,0,0,0)); mdl.setVgap(13); mdl.setHgap(4);
            mdl.setAlignment(Pos.CENTER); mdl.setStyle(bg); mdl.add(kx,0,0); mdl.add(g,0,1);
            
            wf.setOnKeyTyped(hu->{
                char[] hl=hu.getCharacter().toCharArray();
                for(char ko:hl) {
                    if(Character.isLetter(ko)) { 
                        if(csi!=null) { csi.close(); }
            Label u=new Label("Please Enter Digits"); u.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            Button v=new Button("Close"); v.setPrefSize(187,31);
            v.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(27); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(u,0,0); gf.add(v,0,1);
            
            csi=new Stage(); csi.setWidth(274); csi.setHeight(157); csi.initStyle(StageStyle.UNDECORATED);
            csi.setScene(new Scene(gf)); csi.initOwner(cs); csi.show(); 
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                csi.setX(er.getScreenX()-xoff); csi.setY(er.getScreenY()-yoff);
            });
            v.setOnAction(tr->{ 
                csi.close(); wf.setText("");
            });
                    }
                }
            });
            
            hf.setOnKeyTyped(hu->{
                char[] hl=hu.getCharacter().toCharArray();
                for(char ko:hl) {
                    if(Character.isLetter(ko)) { 
                        if(csi!=null) { csi.close(); }
            Label u=new Label("Please Enter Digits"); u.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            Button v=new Button("Close"); v.setPrefSize(187,31);
            v.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(27); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(u,0,0); gf.add(v,0,1);
            
            csi=new Stage(); csi.setWidth(274); csi.setHeight(157); csi.initStyle(StageStyle.UNDECORATED);
            csi.setScene(new Scene(gf)); csi.initOwner(cs); csi.show(); 
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                csi.setX(er.getScreenX()-xoff); csi.setY(er.getScreenY()-yoff);
            });
            v.setOnAction(tr->{
                csi.close(); hf.setText("");
            });
                    }
                }
            });
            
            mwf.setOnKeyTyped(hu->{
                char[] hl=hu.getCharacter().toCharArray();
                for(char ko:hl) {
                    if(Character.isLetter(ko)) { 
                        if(csi!=null) { csi.close(); }
            Label u=new Label("Please Enter Digits"); u.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            Button v=new Button("Close"); v.setPrefSize(187,31);
            v.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(27); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(u,0,0); gf.add(v,0,1);
            
            csi=new Stage(); csi.setWidth(274); csi.setHeight(157); csi.initStyle(StageStyle.UNDECORATED);
            csi.setScene(new Scene(gf)); csi.initOwner(cs); csi.show(); 
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                csi.setX(er.getScreenX()-xoff); csi.setY(er.getScreenY()-yoff);
            });
            v.setOnAction(tr->{
                csi.close(); mwf.setText("");
            });
                    }
                }
            });
            
            mhf.setOnKeyTyped(hu->{
                char[] hl=hu.getCharacter().toCharArray();
                for(char ko:hl) {
                    if(Character.isLetter(ko)) { 
                        if(csi!=null) { csi.close(); }
            Label u=new Label("Please Enter Digits"); u.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            Button v=new Button("Close"); v.setPrefSize(187,31);
            v.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(27); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(u,0,0); gf.add(v,0,1);
            
            csi=new Stage(); csi.setWidth(274); csi.setHeight(157); csi.initStyle(StageStyle.UNDECORATED);
            csi.setScene(new Scene(gf)); csi.initOwner(cs); csi.show(); 
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                csi.setX(er.getScreenX()-xoff); csi.setY(er.getScreenY()-yoff);
            });
            v.setOnAction(tr->{
                csi.close(); mhf.setText("");
            });
                    }
                }
            });
            
            mxwf.setOnKeyTyped(hu->{
                char[] hl=hu.getCharacter().toCharArray();
                for(char ko:hl) {
                    if(Character.isLetter(ko)) { 
                        if(csi!=null) { csi.close(); }
            Label u=new Label("Please Enter Digits"); u.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            Button v=new Button("Close"); v.setPrefSize(187,31);
            v.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(27); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(u,0,0); gf.add(v,0,1);
            
            csi=new Stage(); csi.setWidth(274); csi.setHeight(157); csi.initStyle(StageStyle.UNDECORATED);
            csi.setScene(new Scene(gf)); csi.initOwner(cs); csi.show(); 
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                csi.setX(er.getScreenX()-xoff); csi.setY(er.getScreenY()-yoff);
            });
            v.setOnAction(tr->{
                csi.close(); mxwf.setText("");
            });
                    }
                }
            });
            
            mxhf.setOnKeyTyped(hu->{
                char[] hl=hu.getCharacter().toCharArray();
                for(char ko:hl) {
                    if(Character.isLetter(ko)) { 
                        if(csi!=null) { csi.close(); }
            Label u=new Label("Please Enter Digits"); u.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            Button v=new Button("Close"); v.setPrefSize(187,31);
            v.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(27); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(u,0,0); gf.add(v,0,1);
            
            csi=new Stage(); csi.setWidth(274); csi.setHeight(157); csi.initStyle(StageStyle.UNDECORATED);
            csi.setScene(new Scene(gf)); csi.initOwner(cs); csi.show(); 
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                csi.setX(er.getScreenX()-xoff); csi.setY(er.getScreenY()-yoff);
            });
            v.setOnAction(tr->{
                csi.close(); mxhf.setText("");
            });
                    }
                }
            });
            
            //btm
            Button kk=new Button("OK"); Button clc=new Button("Close"); Button apl=new Button("Apply"); 
            kk.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            clc.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            apl.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            kk.setPrefSize(187,31); clc.setPrefSize(187,31);  apl.setPrefSize(187,31); 
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(3); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(kk,0,0); gf.add(clc,1,0); gf.add(apl,2,0);
            
            BorderPane bdf=new BorderPane(); bdf.setPadding(new Insets(0,0,0,0)); 
            bdf.setTop(k); bdf.setCenter(mdl); bdf.setBottom(gf);                       Scene sdn=new Scene(bdf);
            
            cs=new Stage(); cs.setWidth(689); cs.setHeight(447); cs.initStyle(StageStyle.UNDECORATED);
            cs.setScene(sdn); cs.initOwner(Stage); cs.show(); 
            
            //t actions
            brt.setOnAction(p->{
                FileChooser ch=new FileChooser();
                ch.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG IMAGE","*.png"), 
                        new FileChooser.ExtensionFilter("JPG IMAGE","*.jpg"),
                        new FileChooser.ExtensionFilter("ICO IMAGE","*.ico"));
                
                File hgi=ch.showOpenDialog(Stage);
                if(hgi!=null) {
                    icnf.setText(hgi.toString());
                }
            });
            
            k.setOnMousePressed(yt->{
                xoff=yt.getSceneX(); yoff=yt.getSceneY();
            });
            k.setOnMouseDragged(ty->{
                cs.setX(ty.getScreenX()-xoff); cs.setY(ty.getScreenY()-yoff);
            });
            
            kk.setOnAction(yt->{
                Properties ui=new Properties();
                ui.put("Sdecr",cbx.getValue().toString()); ui.put("Stitle",titf.getText()); ui.put("Sicon","icon.png");
                ui.put("Spcty",ctyf.getValue().toString()); ui.put("Srsz", rszf.getValue().toString()); 
                ui.put("Swidth",wf.getText()); ui.put("Sheight",hf.getText()); ui.put("minW",mwf.getText()); 
                ui.put("minH",mhf.getText()); ui.put("maxW",mxwf.getText()); ui.put("maxH",mxhf.getText());
                
                //Saving Data
                try {
                FileOutputStream in=new FileOutputStream(curr+"/AppData.dll");
                ObjectOutputStream obju=new ObjectOutputStream(in);
                obju.writeObject(ui); obju.flush(); obju.close();
                
                //writing icon File
                File fs=new File(curr+"/icon.png"); File se=new File(icnf.getText());
                
                if(!icnf.getText().equals("icon.png")) {
                copyFile(se, fs); }
                
                } catch (IOException ex) {
                    System.out.println("Save pr change error...");
                }
                cs.close();
                
            });
            
            apl.setOnAction(ty->{ apl.setDisable(true); 
                Properties ui=new Properties();
                ui.put("Sdecr",cbx.getValue().toString()); ui.put("Stitle",titf.getText()); ui.put("Sicon","icon.png");
                ui.put("Spcty",ctyf.getValue().toString()); ui.put("Srsz", rszf.getValue().toString()); 
                ui.put("Swidth",wf.getText()); ui.put("Sheight",hf.getText()); ui.put("minW",mwf.getText()); 
                ui.put("minH",mhf.getText()); ui.put("maxW",mxwf.getText()); ui.put("maxH",mxhf.getText());
                
                //Saving Data
                try {
                FileOutputStream in=new FileOutputStream(curr+"/AppData.dll");
                ObjectOutputStream obju=new ObjectOutputStream(in);
                obju.writeObject(ui); obju.flush(); obju.close();
                
                //writing icon File
                File fs=new File(curr+"/icon.png"); File se=new File(icnf.getText());
                if(!icnf.getText().equals("icon.png")) {
                copyFile(se, fs); } 
                
                } catch (IOException ex) {
                    System.out.println("Save pr change error...");
                }
            });
            
            clc.setOnAction(yt->{
                cs.close();
            });
            
            
        } else { if(cs!=null) { cs.close(); }
            Label h=new Label("No Project Is Open"); h.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            Button v=new Button("Close"); v.setPrefSize(187,31);
            v.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(27); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(h,0,0); gf.add(v,0,1);
            
            cs=new Stage(); cs.setWidth(274); cs.setHeight(157); cs.initStyle(StageStyle.UNDECORATED);
            cs.setScene(new Scene(gf)); cs.initOwner(Stage); cs.show();
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                cs.setX(er.getScreenX()-xoff); cs.setY(er.getScreenY()-yoff);
            });
            v.setOnAction(tr->{
                cs.close();
            });
        }
        });
        
        cp.setOnAction(ev->{  if(curr!=null) {
            lst.remove(new File(curr));
            obls.clear(); obls.addAll(lst); lvw.setItems(obls); 
            
            ObservableList ikh=FXCollections.observableArrayList();
            combo.setItems(ikh); edt.setText(null); curr=null;
            
            //writing
            try {
                FileOutputStream out=new FileOutputStream("proj.dll");
                ObjectOutputStream obju=new ObjectOutputStream(out);
                obju.writeObject(lst); obju.flush(); obju.close();
                
            } catch (IOException ex) {
                System.out.println("close pr error...");
            }
        } else { if(csi!=null) {csi.close();}
            Label u=new Label("No Project Is Open"); u.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            Button v=new Button("Close"); v.setPrefSize(187,31);
            v.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(27); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(u,0,0); gf.add(v,0,1);
            
            csi=new Stage(); csi.setWidth(274); csi.setHeight(157); csi.initStyle(StageStyle.UNDECORATED);
            csi.setScene(new Scene(gf)); csi.initOwner(Stage); csi.show(); 
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                csi.setX(er.getScreenX()-xoff); csi.setY(er.getScreenY()-yoff);
            });
            
            v.setOnAction(tr->{
                csi.close();
            });
        }
        });
        
        xp.setOnAction(ev->{
            if(curr!=null) {
                FileChooser ch=new FileChooser(); 
                ch.setInitialFileName(new File(curr).getName()); ch.setTitle("Export Project");
                
                File fi=ch.showSaveDialog(Stage);
                File fg=new File(curr); 
                
                if(fi!=null) { 
                try {
                FileUtils.copyDirectory(fg, fi);
                } catch (IOException ex) {
                    System.out.println("Export error...");
                } }
                
            } else { if(csi!=null) {csi.close();}
            Label u=new Label("No Project Is Open"); u.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            Button v=new Button("Close"); v.setPrefSize(187,31);
            v.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(27); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(u,0,0); gf.add(v,0,1);
            
            csi=new Stage(); csi.setWidth(274); csi.setHeight(157); csi.initStyle(StageStyle.UNDECORATED);
            csi.setScene(new Scene(gf)); csi.initOwner(Stage); csi.show(); 
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                csi.setX(er.getScreenX()-xoff); csi.setY(er.getScreenY()-yoff);
            });
            
            v.setOnAction(tr->{
                csi.close();
            });
        }
        });
        
        sv.setOnAction(ev->{
            if(curr!=null && combo.getValue()!=null) { try {
                File hg=new File(curr+"/Scene/"+combo.getValue());
                FileWriter wrt=new FileWriter(hg);
                wrt.write(edt.getText()); wrt.flush(); wrt.close(); act.setText(combo.getValue()+" Saved"); fdd.play();
                
            } catch (IOException ex){
                System.out.println("ion Save");
            } 
                
            } else { if(csi!=null) {csi.close();}
            Label u=new Label("No Project/File Is Open"); u.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            Button v=new Button("Close"); v.setPrefSize(187,31);
            v.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(27); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(u,0,0); gf.add(v,0,1);
            
            csi=new Stage(); csi.setWidth(274); csi.setHeight(157); csi.initStyle(StageStyle.UNDECORATED);
            csi.setScene(new Scene(gf)); csi.initOwner(Stage); csi.show(); 
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                csi.setX(er.getScreenX()-xoff); csi.setY(er.getScreenY()-yoff);
            });
            
            v.setOnAction(tr->{
                csi.close();
            });
        }
        });
        
        pr.setOnAction(yu->{
            if(curr!=null && combo.getValue()!=null) {
                PrinterJob job=PrinterJob.createPrinterJob(); 
                if(job!=null && job.showPrintDialog(Stage)) {
                    job.printPage(edt); job.endJob(); 
                }
                
            } else { if(csi!=null) {csi.close();}
            Label u=new Label("No Project Is Open"); u.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            Button v=new Button("Close"); v.setPrefSize(187,31);
            v.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(27); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(u,0,0); gf.add(v,0,1);
            
            csi=new Stage(); csi.setWidth(274); csi.setHeight(157); csi.initStyle(StageStyle.UNDECORATED);
            csi.setScene(new Scene(gf)); csi.initOwner(Stage); csi.show(); 
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                csi.setX(er.getScreenX()-xoff); csi.setY(er.getScreenY()-yoff);
            });
            
            v.setOnAction(tr->{
                csi.close();
            });
        }
        });
        
        xt.setOnAction(ev->{
            String df="",dd; 
        try {
            File gy=new File(curr+"/Scene/"+combo.getValue());
            DataInputStream din=new DataInputStream(new FileInputStream(gy));
            while(din.available()>0) {
                df=df+din.readLine()+"\n";
            } dd=edt.getText();
            
            if(df.equals(dd)) {
                Stage.close();
            } else {                 if(cs!=null) {cs.close(); }
                
                //top
                Label lb=new Label("Save Changes?"); lb.setStyle("-fx-font:normal 19px 'Cambria'; -fx-text-fill:azure");
                GridPane oi=new GridPane(); oi.setPadding(new Insets(7,7,7,7)); oi.setVgap(4); oi.setHgap(4);
                oi.setAlignment(Pos.CENTER); oi.setStyle("-fx-background-color:black"); oi.add(lb,0,0);
                
                //center
                Label msg=new Label("Scene Document Changes Are Not Saved.\nDo you want to save these changes before exiting?");
                msg.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
                GridPane io=new GridPane(); io.setPadding(new Insets(7,7,7,7)); io.setVgap(4); io.setHgap(4);
                io.setAlignment(Pos.CENTER); io.setStyle(bg); io.add(msg,0,0);
                
                //btm
                Button ys=new Button("Save"); Button no=new Button("Discard"); Button ccl=new Button("Cancel");
                ys.setStyle("-fx-background-color:black; -fx-text-fill:azure; -fx-font:normal 16px 'Cambria'");
                no.setStyle("-fx-background-color:black; -fx-text-fill:azure; -fx-font:normal 16px 'Cambria'");
                ccl.setStyle("-fx-background-color:black; -fx-text-fill:azure; -fx-font:normal 16px 'Cambria'");
                ys.setPrefSize(178, 37); no.setPrefSize(178, 37); ccl.setPrefSize(178, 37);
                GridPane oii=new GridPane(); oii.setPadding(new Insets(9,9,9,9)); oii.setVgap(4); oii.setHgap(17);
                oii.setAlignment(Pos.CENTER); oii.setStyle(bg); oii.add(ys,0,0); oii.add(no,1,0); oii.add(ccl,2,0);
                
                BorderPane bdr=new BorderPane(); bdr.setPadding(new Insets(0,0,0,0)); bdr.setTop(oi);
                bdr.setCenter(io); bdr.setBottom(oii); 
                Scene scu=new Scene(bdr); 
                cs=new Stage(); cs.setWidth(589); cs.setHeight(347); cs.initStyle(StageStyle.UNDECORATED);
                cs.setScene(scu); cs.initOwner(Stage); cs.show();
                
                ys.setOnAction(er->{
                    try {
                        FileWriter wrt=new FileWriter(gy);
                        wrt.write(dd); wrt.flush(); wrt.close(); Stage.close();
                    } catch (IOException ex){
                        System.out.println("Save cls error....");
                    }
                });
                
                no.setOnAction(er->{
                    Stage.close();
                });
                
                ccl.setOnAction(er->{
                    cs.close();
                });
                
                oi.setOnMousePressed(er->{
                    xoff=er.getSceneX(); yoff=er.getSceneY();
                });
                oi.setOnMouseDragged(er->{
                    cs.setX(er.getScreenX()-xoff); cs.setY(er.getScreenY()-yoff);
                });
            }
            
        } catch (IOException ex){
            System.out.println("close save error....");
        }
        
        if(combo.getValue()==null) {
            Stage.close();
        }
        });
        
        undo.setOnAction(ev->{
            edt.undo();
        });
        
        redo.setOnAction(ev->{
            edt.redo();
        });
        
        copy.setOnAction(ev->{
            edt.copy();
        });
        
        cut.setOnAction(ev->{
            edt.cut();
        });
        
        pst.setOnAction(ev->{
            edt.paste();
        });
        
        bp.setOnAction(ev->{
            if(curr!=null && combo.getValue()!=null) { try {
                File hg=new File(curr+"/Scene/"+combo.getValue());
                FileWriter wrt=new FileWriter(hg);
                wrt.write(edt.getText()); wrt.flush(); wrt.close(); act.setText(combo.getValue()+" Saved"); fdd.play();
                
            fdg=new FadeTransition(Duration.millis(2879)); fdg.setAutoReverse(false); fdg.setCycleCount(1); 
            fdg.setFromValue(1); fdg.setToValue(1); fdg.setNode(act); act.setText("Building Project");
            fdg.play();
            
            fdg.setOnFinished(er->{
                act.setText("Build Complete"); fdd.play();
            });
                
            } catch (IOException ex){
                System.out.println("ion Save");
            } 
                
            } else { if(csi!=null) {csi.close();}
            Label u=new Label("No Project/File Is Open"); u.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            Button v=new Button("Close"); v.setPrefSize(187,31);
            v.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(27); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(u,0,0); gf.add(v,0,1);
            
            csi=new Stage(); csi.setWidth(274); csi.setHeight(157); csi.initStyle(StageStyle.UNDECORATED);
            csi.setScene(new Scene(gf)); csi.initOwner(Stage); csi.show(); 
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                csi.setX(er.getScreenX()-xoff); csi.setY(er.getScreenY()-yoff);
            });
            
            v.setOnAction(tr->{
                csi.close();
            });
        }
            
        });
        
        cb.setOnAction(ev->{
            if(curr!=null && combo.getValue()!=null) { try {
                File hg=new File(curr+"/Scene/"+combo.getValue());
                FileWriter wrt=new FileWriter(hg);
                wrt.write(edt.getText()); wrt.flush(); wrt.close(); act.setText(combo.getValue()+" Saved"); fdd.play();
                
            fdg=new FadeTransition(Duration.millis(1978)); fdg.setAutoReverse(false); fdg.setCycleCount(1); 
            fdg.setFromValue(1); fdg.setToValue(1); fdg.setNode(act); act.setText("Cleaning Project");
            fdg.play();
            
            fdg2=new FadeTransition(Duration.millis(2879)); fdg2.setAutoReverse(false); fdg2.setCycleCount(1); 
            fdg2.setFromValue(1); fdg2.setToValue(1); fdg2.setNode(act);
            
            fdg.setOnFinished(er->{
                act.setText("Building Project"); fdg2.play();
            });
            
            fdg2.setOnFinished(yr->{
                act.setText("Build Complete"); fdd.play();
            });
                
            } catch (IOException ex){
                System.out.println("ion Save");
            } 
                
            } else { if(csi!=null) {csi.close();}
            Label u=new Label("No Project/File Is Open"); u.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            Button v=new Button("Close"); v.setPrefSize(187,31);
            v.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(27); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(u,0,0); gf.add(v,0,1);
            
            csi=new Stage(); csi.setWidth(274); csi.setHeight(157); csi.initStyle(StageStyle.UNDECORATED);
            csi.setScene(new Scene(gf)); csi.initOwner(Stage); csi.show(); 
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                csi.setX(er.getScreenX()-xoff); csi.setY(er.getScreenY()-yoff);
            });
            
            v.setOnAction(tr->{
                csi.close();
            });
        }
        });
        
        rp.setOnAction(ev->{
            if(curr!=null && combo.getValue()!=null) { try {
                File hg=new File(curr+"/Scene/"+combo.getValue());
                FileWriter wrt=new FileWriter(hg);
                wrt.write(edt.getText()); wrt.flush(); wrt.close(); act.setText(combo.getValue()+" Saved"); fdd.play();
                
            fdg=new FadeTransition(Duration.millis(2879)); fdg.setAutoReverse(false); fdg.setCycleCount(1); 
            fdg.setFromValue(1); fdg.setToValue(1); fdg.setNode(act); act.setText("Running Project");
            fdg.play();
            
            fdg.setOnFinished(er->{
                act.setText(""); fdd.play();
            });
            
            File d=new File(curr+"/"+new File(curr).getName()+".exe"); //System.out.println(d);
            Desktop dsk=Desktop.getDesktop(); dsk.open(d); 
                
            } catch (IOException ex){
                System.out.println("ion Save");
            } 
                
            } else { if(csi!=null) {csi.close();}
            Label u=new Label("No Project/File Is Open"); u.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            Button v=new Button("Close"); v.setPrefSize(187,31);
            v.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(27); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(u,0,0); gf.add(v,0,1);
            
            csi=new Stage(); csi.setWidth(274); csi.setHeight(157); csi.initStyle(StageStyle.UNDECORATED);
            csi.setScene(new Scene(gf)); csi.initOwner(Stage); csi.show(); 
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                csi.setX(er.getScreenX()-xoff); csi.setY(er.getScreenY()-yoff);
            });
            
            v.setOnAction(tr->{
                csi.close();
            });
        }
        });
        
        hd.setOnAction(ev->{ Stage.close();
            try {
            Desktop dsk=Desktop.getDesktop(); dsk.open(new File("SMXDEV.exe"));
            } catch (IOException ex){
                System.out.println("restart error....");
            }
        });
        
        cu.setOnAction(ev->{ if(csi!=null) {csi.close();}
            try {
            URL url=new URL("http://wwww.oracle.com/index.html");
            
            ProgressIndicator indl=new ProgressIndicator(); indl.setStyle("-fx-progress-color:azure");
            indl.setPrefSize(57,57);
            Label vi=new Label("Checking for Updates"); Button v=new Button("OK"); v.setPrefSize(137,31);
            vi.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:white");
            v.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(17); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(indl,0,0); gf.add(vi,0,1); gf.add(v,0,2);
            
            csi=new Stage(); csi.setWidth(574); csi.setHeight(349); csi.initStyle(StageStyle.UNDECORATED);
            csi.setScene(new Scene(gf)); csi.initOwner(Stage); csi.show(); 
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                csi.setX(er.getScreenX()-xoff); csi.setY(er.getScreenY()-yoff);
            });
            
            v.setOnAction(tr->{
                csi.close();
            });
            
            if(getFileSize(url)>0) {
                vi.setText("No Updates Are Available. \nThis Application Is Upto Date."); indl.setProgress(1);
            } else {
                vi.setText("Connection Error.\nPlease check your internet connection and try again.");
            }
            
            } catch (MalformedURLException ex) {
                System.out.println("Malformed URL error..."); 
            }
        });
        
        ab.setOnAction(ev->{
            
            WebView w=new WebView(); WebEngine e=w.getEngine();
            w.setPrefSize(871,557); Button v=new Button("Close"); v.setPrefSize(138,31); 
            v.setStyle("-fx-background-color:black; -fx-text-fill:azure; -fx-font:normal 16px 'Cambria'");
            
            try {
            //loading abt file
            File f=new File("AbtDoc.html"); URL url=f.toURI().toURL();
            e.load(url.toString());
            
            } catch (MalformedURLException ex){
                System.out.println("URL error");
            }
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(4,4,4,4)); gf.setVgap(3); gf.setHgap(0);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(w,0,0); gf.add(v,0,1);
            
            csi=new Stage(); csi.setWidth(874); csi.setHeight(598); csi.initStyle(StageStyle.UNDECORATED);
            csi.setScene(new Scene(gf)); csi.initOwner(Stage); csi.show(); 
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                csi.setX(er.getScreenX()-xoff); csi.setY(er.getScreenY()-yoff);
            });
            
            v.setOnAction(tr->{
                csi.close();
            });
        });
        
        
        
        
        
        //Actions Btns
        npb.setOnAction(ev->{
            if(cs!=null) { cs.close(); }
            Label crt=new Label("Create New Project"); Label prn=new Label("Project Name");
            Label prol=new Label("Project Location"); Label prolv=new Label(prdir+""); TextField prnf=new TextField(); 
            Button crtp=new Button("Create"); Button clsi=new Button("Close"); Label ext=new Label();
            
            //Styling my nodes
            crt.setStyle("-fx-font:normal 19px 'Cambria'; -fx-text-fill:azure;");
            prn.setStyle("-fx-font:normal 18px 'Cambria'; -fx-text-fill:azure;");
            prol.setStyle("-fx-font:normal 18px 'Cambria'; -fx-text-fill:azure;");
            prolv.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:lightsteelblue;");
            prnf.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:darkgreen;");
            crtp.setStyle("-fx-background-color:black;-fx-text-fill:azure;-fx-font:normal 18px 'Cambria'");
            clsi.setStyle("-fx-background-color:black;-fx-text-fill:azure;-fx-font:normal 18px 'Cambria'");
            ext.setStyle("-fx-font:bold 16px 'Cambria'; -fx-text-fill:black;");
            
            crtp.setPrefSize(118,37); clsi.setPrefSize(118,37);
            
            GridPane upn=new GridPane(); upn.setPadding(new Insets(8,8,8,8)); upn.setVgap(18); upn.setHgap(13);
            upn.setAlignment(Pos.CENTER); upn.setStyle(bg); upn.add(prn,0,0); upn.add(prnf,1,0);
            upn.add(prol,0,1); upn.add(prolv,1,1); upn.add(ext,1,4);
            
            GridPane hdr=new GridPane(); hdr.setPadding(new Insets(5,5,5,5)); hdr.setStyle("-fx-background-color:black");
            hdr.setAlignment(Pos.CENTER); hdr.add(crt,0,0); 
            
            GridPane lwe=new GridPane(); lwe.setAlignment(Pos.CENTER_RIGHT); lwe.setPadding(new Insets(9,9,9,9));
            lwe.setHgap(14); lwe.setStyle(bg);
            lwe.add(crtp,0,0); lwe.add(clsi,1,0);
            
            BorderPane bdr=new BorderPane(); bdr.setPadding(new Insets(0,0,0,0)); bdr.setTop(hdr); bdr.setCenter(upn);
            bdr.setBottom(lwe);
            
            cs=new Stage(); cs.setWidth(589); cs.setHeight(347); cs.initStyle(StageStyle.UNDECORATED);
            Scene hg=new Scene(bdr); cs.setScene(hg); cs.initOwner(Stage); cs.show();
            
            clsi.setOnAction(evv->{
                cs.close();
            });
            
            hdr.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            
            hdr.setOnMouseDragged(evr->{
                cs.setX(evr.getScreenX()-xoff); cs.setY(evr.getScreenY()-yoff);
            });
            
            prnf.setOnKeyReleased(eri->{
                prolv.setText(prdir+prnf.getText());
            });
            
            crtp.setOnAction(yu->{
                //load project file location
                fll=new File(prdir+prnf.getText()); 
                if(!fll.exists()) { fll.mkdirs();  
                File sci=new File(prdir+prnf.getText()+"/Scene");
                if(!sci.exists()) { sci.mkdirs(); }
                
                //create project files
                File bldt=new File("BuildTools/compiler.bin");
                File bldd=new File("BuildTools/AppData.dll");
                File bldi=new File("BuildTools/icon.png");
                
                File ap=new File(prdir+prnf.getText()+"/"+prnf.getText()+".exe");
                File dt=new File(prdir+prnf.getText()+"/"+"AppData.dll");
                File ic=new File(prdir+prnf.getText()+"/"+"icon.png");
                
                //copying files
                try {
                    copyFile(bldt, ap);
                    copyFile(bldd,dt);
                    copyFile(bldi,ic);
                    
                    //creating HTML template
                    File tpl=new File("BuildTools/template.tmp"); String rdt="";
                    DataInputStream inda=new DataInputStream(new FileInputStream(tpl.getAbsolutePath()));
                    while (inda.available()>0) {
                        rdt=rdt+inda.readLine()+"\n";
                    }
                    
                    File idx=new File(prdir+prnf.getText()+"/Scene/index.html");
                    FileWriter wrt=new FileWriter(idx);
                    wrt.write(rdt); wrt.flush(); wrt.close();
                    
                    //applying data
                    combo.setValue("index.html"); edt.setText(rdt); cs.close(); kih="index.html";
                    
                    //adding to prjcts menu and curr
                    curr=prdir+prnf.getText(); System.out.println("Curr:"+curr);
                    
                    lst.add(new File(prdir+prnf.getText())); obls.clear(); obls.addAll(lst); lvw.setItems(obls);
                    
                    cbc=sci.list(); List<String> uip=new ArrayList();
                    uip.addAll(Arrays.asList(cbc));
                    
                    ObservableList on=FXCollections.observableArrayList(uip); combo.setItems(on);
                    
                    //writing lst
                    FileOutputStream outy=new FileOutputStream("proj.dll");
                    ObjectOutputStream objy=new ObjectOutputStream(outy);
                    objy.writeObject(lst); objy.flush(); objy.close(); ext.setText("");
                    
                } catch (IOException ex) {
                    System.out.println(ex.getCause());
                }
                }  else {
                    ext.setText("Project Name Already Exists.");
                }
            });
        });
        
        opb.setOnAction(ev->{
            if(cs!=null) {cs.close();}
            //top dlg
            Label ope=new Label("Open Project"); ope.setStyle("-fx-font:normal 19px 'Cambria'; -fx-text-fill:azure");
            GridPane oe=new GridPane(); oe.setPadding(new Insets(7,7,7,7)); oe.setVgap(4); oe.setHgap(4);
            oe.setAlignment(Pos.CENTER); oe.setStyle("-fx-background-color:black"); oe.add(ope,0,0);
            
            //contect
            Label ope1=new Label(prdir); ope1.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            File lvd=new File(prdir); cbc=lvd.list(); List fy=new ArrayList(); 
            fy.addAll(Arrays.asList(cbc)); ObservableList obn=FXCollections.observableArrayList(fy);
            ListView lvm=new ListView(); lvm.setItems(obn); lvm.setPrefSize(478,248); 
            lvm.setStyle("-fx-control-inner-background:black; -fx-font:normal 15px 'Cambria'");
            
            GridPane oe1=new GridPane(); oe1.setPadding(new Insets(2,2,2,2)); oe1.setVgap(7); oe1.setHgap(8);
            oe1.setAlignment(Pos.CENTER); oe1.setStyle(bg); oe1.add(ope1,0,0); oe1.add(lvm,0,1); 
            
            //btns btm
            Button opnn=new Button("Open"); Button clc=new Button("Cancel"); Label yui=new Label();
            opnn.setStyle("-fx-background-color:black;-fx-text-fill:azure;-fx-font:normal 18px 'Cambria'");
            clc.setStyle("-fx-background-color:black;-fx-text-fill:azure;-fx-font:normal 18px 'Cambria'");
            yui.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            clc.setPrefSize(87,34); opnn.setPrefSize(87,34);
            
            GridPane oe2=new GridPane(); oe2.setPadding(new Insets(3,3,3,3)); oe2.setVgap(3); oe2.setHgap(4);
            oe2.setAlignment(Pos.CENTER_RIGHT); oe2.setStyle(bg); oe2.add(yui,0,0); oe2.add(opnn,1,0); oe2.add(clc,2,0); 
            
            BorderPane bpo=new BorderPane(); bpo.setPadding(new Insets(0,0,0,0)); bpo.setTop(oe); bpo.setCenter(oe1);
            bpo.setBottom(oe2);
            
            cs=new Stage(); cs.setWidth(587); cs.setHeight(357); cs.initStyle(StageStyle.UNDECORATED);
            Scene hg=new Scene(bpo); cs.setScene(hg); cs.initOwner(Stage); cs.show();
            
            oe.setOnMousePressed(evt->{
                xoff=evt.getSceneX(); yoff=evt.getSceneY();
            });
            oe.setOnMouseDragged(ok->{
                cs.setX(ok.getScreenX()-xoff); cs.setY(ok.getScreenY()-yoff);
            });
            
            clc.setOnAction(rt->{
                cs.close();
            });
            
            opnn.setOnAction(tc->{
                if(lvm.getSelectionModel().getSelectedItem()!=null) {
                    String lkj=lvm.getSelectionModel().getSelectedItem().toString();
                    File eds=new File(prdir+lkj);
                    String[] hs=eds.list(); List<String> km=new ArrayList();
                    km.addAll(Arrays.asList(hs));
                    
                    if(km.contains("AppData.dll") && km.contains(lkj+".exe") && km.contains("Scene") && km.contains("icon.png")){
                        
                        if(!lst.contains(new File(prdir+"/"+lkj))) {
                            lst.add(eds);
                            try {
                            FileOutputStream oju=new FileOutputStream("proj.dll");
                            ObjectOutputStream ih=new ObjectOutputStream(oju);
                            ih.writeObject(lst); ih.flush(); ih.close();  
                            
                            obls.clear(); obls.addAll(lst); lvw.setItems(obls); cs.close();
                            
                            } catch (IOException ex) {
                                System.out.println("wrt error");
                            }
                        } else {
                            yui.setText("Project Is Already Open!"); cs.close();
                        }
                        
                    } else {
                        yui.setText("The Selected Project Is Corrupt!");
                    }
                    
                } else {
                    yui.setText("Please Select Project!");
                }
            });
        });
        
        clb.setOnAction(ev->{
            if(curr!=null) {
            lst.remove(new File(curr));
            obls.clear(); obls.addAll(lst); lvw.setItems(obls); 
            
            ObservableList ikh=FXCollections.observableArrayList();
            combo.setItems(ikh); edt.setText(null); curr=null;
            
            //writing
            try {
                FileOutputStream out=new FileOutputStream("proj.dll");
                ObjectOutputStream obju=new ObjectOutputStream(out);
                obju.writeObject(lst); obju.flush(); obju.close();
                
            } catch (IOException ex) {
                System.out.println("close pr error...");
            }
        } else { if(csi!=null) {csi.close();}
            Label u=new Label("No Project Is Open"); u.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            Button v=new Button("Close"); v.setPrefSize(187,31);
            v.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(27); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(u,0,0); gf.add(v,0,1);
            
            csi=new Stage(); csi.setWidth(274); csi.setHeight(157); csi.initStyle(StageStyle.UNDECORATED);
            csi.setScene(new Scene(gf)); csi.initOwner(Stage); csi.show(); 
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                csi.setX(er.getScreenX()-xoff); csi.setY(er.getScreenY()-yoff);
            });
            
            v.setOnAction(tr->{
                csi.close();
            });
        }
        });
        
        prb.setOnAction(ev->{
            if(curr!=null) {  if(cs!=null) { cs.close(); }
            
            
            //top
            Label j=new Label("Project Properties"); j.setStyle("-fx-font:normal 19px 'Cambria'; -fx-text-fill:azure");
            GridPane k=new GridPane(); k.setPadding(new Insets(5,5,5,5)); k.setVgap(0); k.setHgap(0);
            k.setStyle("-fx-background-color:black"); k.setAlignment(Pos.CENTER); k.add(j,0,0);
            
            //reading pr data
            Properties r=new Properties();
            try {
            File p=new File(curr+"/AppData.dll"); 
            FileInputStream in =new FileInputStream(p);
            ObjectInputStream obji=new ObjectInputStream(in);
            r=(Properties)obji.readObject();
            
            } catch (IOException | ClassNotFoundException er) {
                System.out.println("Error reading File");
            }
            
            //content
            Label vis=new Label("Visual"); vis.setStyle("-fx-font:bold 17px 'Cambria'; -fx-text-fill:white");
            
            Label dec=new Label("Window Decoration"); Label tit=new Label("Window Title"); 
            Label opct=new Label("Window Opacity"); Label cn=new Label("Window Icon"); 
            Label rsz=new Label("Window Resizing");
            
            ComboBox cbx=new ComboBox(); ObservableList vx=FXCollections.observableArrayList("Default","SeumxStyle");
            cbx.setItems(vx); cbx.setValue(r.getProperty("Sdecr"));
            TextField titf=new TextField(r.getProperty("Stitle")); TextField icnf=new TextField(r.getProperty("Sicon"));
             ChoiceBox ctyf=new ChoiceBox(); ctyf.setValue(r.getProperty("Spcty"));
            ObservableList ux=FXCollections.observableArrayList("0.1","0.2","0.3","0.4","0.5","0.6","0.7","0.8","0.9","1");
            ctyf.setItems(ux); Button brt=new Button("..."); 
            brt.setStyle("-fx-font:normal 15px 'Cambria'; -fx-text-fill:navy; -fx-background-color:aliceblue");
            ChoiceBox rszf=new ChoiceBox(); rszf.setValue(r.getProperty("Srsz"));
            ObservableList t=FXCollections.observableArrayList("true","false"); rszf.setItems(t);
            
            dec.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            tit.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            opct.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            cn.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            rsz.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            
            cbx.setStyle("-fx-font:normal 15px 'Cambria'"); titf.setStyle("-fx-font:normal 15px 'Cambria'");
            icnf.setStyle("-fx-font:normal 15px 'Cambria'"); ctyf.setStyle("-fx-font:normal 15px 'Cambria'");
            rszf.setStyle("-fx-font:normal 15px 'Cambria'");
            
            GridPane kx=new GridPane(); kx.setPadding(new Insets(4,4,4,4)); kx.setVgap(5); kx.setHgap(8);
            kx.setAlignment(Pos.CENTER); kx.setStyle("-fx-background-color:black");
            kx.add(vis,0,0); kx.add(dec,0,2); kx.add(cbx,1,2); kx.add(tit,0,3); kx.add(titf,1,3); kx.add(opct,0,4);
            kx.add(ctyf,1,4); kx.add(cn,0,5); kx.add(icnf,1,5); kx.add(brt,2,5); kx.add(rsz,0,6); kx.add(rszf,1,6);
            
            Label i=new Label("Size"); 
            Label w=new Label("Width"); Label h=new Label("Height"); Label mw=new Label("Minimum Width");
            Label mh=new Label("Minimum Height"); Label mxw=new Label("Max Width"); Label mxh=new Label("Max Height");
            TextField wf=new TextField(r.getProperty("Swidth")); TextField hf=new TextField(r.getProperty("Sheight"));
            TextField mwf=new TextField(r.getProperty("minW")); TextField mhf=new TextField(r.getProperty("minH"));
            TextField mxwf=new TextField(r.getProperty("maxW")); TextField mxhf=new TextField(r.getProperty("maxH"));
            
            i.setStyle("-fx-font:bold 17px 'Cambria'; -fx-text-fill:white");
            w.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            h.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            mw.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            mh.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            mxw.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            mxh.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            
            wf.setStyle("-fx-font:normal 15px 'Cambria'"); hf.setStyle("-fx-font:normal 15px 'Cambria'");
            mwf.setStyle("-fx-font:normal 15px 'Cambria'"); mhf.setStyle("-fx-font:normal 15px 'Cambria'");
            mxwf.setStyle("-fx-font:normal 15px 'Cambria'"); mxhf.setStyle("-fx-font:normal 15px 'Cambria'");
            
            GridPane g=new GridPane(); g.setPadding(new Insets(7,7,7,7)); g.setVgap(5); g.setHgap(8);
            g.setAlignment(Pos.CENTER); g.setStyle("-fx-background-color:black");
            g.add(i,0,0); g.add(w,0,2); g.add(wf,1,2); g.add(h,2,2); g.add(hf,3,2);
            g.add(mw,0,3); g.add(mwf,1,3); g.add(mh,2,3); g.add(mhf,3,3);
            g.add(mxw,0,4); g.add(mxwf,1,4); g.add(mxh,2,4); g.add(mxhf,3,4);
            
            GridPane mdl=new GridPane(); mdl.setPadding(new Insets(0,0,0,0)); mdl.setVgap(13); mdl.setHgap(4);
            mdl.setAlignment(Pos.CENTER); mdl.setStyle(bg); mdl.add(kx,0,0); mdl.add(g,0,1);
            
            wf.setOnKeyTyped(hu->{
                char[] hl=hu.getCharacter().toCharArray();
                for(char ko:hl) {
                    if(Character.isLetter(ko)) { 
                        if(csi!=null) { csi.close(); }
            Label u=new Label("Please Enter Digits"); u.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            Button v=new Button("Close"); v.setPrefSize(187,31);
            v.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(27); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(u,0,0); gf.add(v,0,1);
            
            csi=new Stage(); csi.setWidth(274); csi.setHeight(157); csi.initStyle(StageStyle.UNDECORATED);
            csi.setScene(new Scene(gf)); csi.initOwner(cs); csi.show(); 
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                csi.setX(er.getScreenX()-xoff); csi.setY(er.getScreenY()-yoff);
            });
            v.setOnAction(tr->{
                csi.close(); wf.setText("");
            });
                    }
                }
            });
            
            hf.setOnKeyTyped(hu->{
                char[] hl=hu.getCharacter().toCharArray();
                for(char ko:hl) {
                    if(Character.isLetter(ko)) { 
                        if(csi!=null) { csi.close(); }
            Label u=new Label("Please Enter Digits"); u.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            Button v=new Button("Close"); v.setPrefSize(187,31);
            v.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(27); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(u,0,0); gf.add(v,0,1);
            
            csi=new Stage(); csi.setWidth(274); csi.setHeight(157); csi.initStyle(StageStyle.UNDECORATED);
            csi.setScene(new Scene(gf)); csi.initOwner(cs); csi.show(); 
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                csi.setX(er.getScreenX()-xoff); csi.setY(er.getScreenY()-yoff);
            });
            v.setOnAction(tr->{
                csi.close(); hf.setText("");
            });
                    }
                }
            });
            
            mwf.setOnKeyTyped(hu->{
                char[] hl=hu.getCharacter().toCharArray();
                for(char ko:hl) {
                    if(Character.isLetter(ko)) { 
                        if(csi!=null) { csi.close(); }
            Label u=new Label("Please Enter Digits"); u.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            Button v=new Button("Close"); v.setPrefSize(187,31);
            v.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(27); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(u,0,0); gf.add(v,0,1);
            
            csi=new Stage(); csi.setWidth(274); csi.setHeight(157); csi.initStyle(StageStyle.UNDECORATED);
            csi.setScene(new Scene(gf)); csi.initOwner(cs); csi.show(); 
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                csi.setX(er.getScreenX()-xoff); csi.setY(er.getScreenY()-yoff);
            });
            v.setOnAction(tr->{
                csi.close(); mwf.setText("");
            });
                    }
                }
            });
            
            mhf.setOnKeyTyped(hu->{
                char[] hl=hu.getCharacter().toCharArray();
                for(char ko:hl) {
                    if(Character.isLetter(ko)) { 
                        if(csi!=null) { csi.close(); }
            Label u=new Label("Please Enter Digits"); u.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            Button v=new Button("Close"); v.setPrefSize(187,31);
            v.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(27); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(u,0,0); gf.add(v,0,1);
            
            csi=new Stage(); csi.setWidth(274); csi.setHeight(157); csi.initStyle(StageStyle.UNDECORATED);
            csi.setScene(new Scene(gf)); csi.initOwner(cs); csi.show(); 
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                csi.setX(er.getScreenX()-xoff); csi.setY(er.getScreenY()-yoff);
            });
            v.setOnAction(tr->{
                csi.close(); mhf.setText("");
            });
                    }
                }
            });
            
            mxwf.setOnKeyTyped(hu->{
                char[] hl=hu.getCharacter().toCharArray();
                for(char ko:hl) {
                    if(Character.isLetter(ko)) { 
                        if(csi!=null) { csi.close(); }
            Label u=new Label("Please Enter Digits"); u.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            Button v=new Button("Close"); v.setPrefSize(187,31);
            v.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(27); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(u,0,0); gf.add(v,0,1);
            
            csi=new Stage(); csi.setWidth(274); csi.setHeight(157); csi.initStyle(StageStyle.UNDECORATED);
            csi.setScene(new Scene(gf)); csi.initOwner(cs); csi.show(); 
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                csi.setX(er.getScreenX()-xoff); csi.setY(er.getScreenY()-yoff);
            });
            v.setOnAction(tr->{
                csi.close(); mxwf.setText("");
            });
                    }
                }
            });
            
            mxhf.setOnKeyTyped(hu->{
                char[] hl=hu.getCharacter().toCharArray();
                for(char ko:hl) {
                    if(Character.isLetter(ko)) { 
                        if(csi!=null) { csi.close(); }
            Label u=new Label("Please Enter Digits"); u.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            Button v=new Button("Close"); v.setPrefSize(187,31);
            v.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(27); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(u,0,0); gf.add(v,0,1);
            
            csi=new Stage(); csi.setWidth(274); csi.setHeight(157); csi.initStyle(StageStyle.UNDECORATED);
            csi.setScene(new Scene(gf)); csi.initOwner(cs); csi.show(); 
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                csi.setX(er.getScreenX()-xoff); csi.setY(er.getScreenY()-yoff);
            });
            v.setOnAction(tr->{
                csi.close(); mxhf.setText("");
            });
                    }
                }
            });
            
            //btm
            Button kk=new Button("OK"); Button clc=new Button("Close"); Button apl=new Button("Apply"); 
            kk.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            clc.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            apl.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            kk.setPrefSize(187,31); clc.setPrefSize(187,31);  apl.setPrefSize(187,31); 
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(3); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(kk,0,0); gf.add(clc,1,0); gf.add(apl,2,0);
            
            BorderPane bdf=new BorderPane(); bdf.setPadding(new Insets(0,0,0,0)); 
            bdf.setTop(k); bdf.setCenter(mdl); bdf.setBottom(gf);                       Scene sdn=new Scene(bdf);
            
            cs=new Stage(); cs.setWidth(689); cs.setHeight(447); cs.initStyle(StageStyle.UNDECORATED);
            cs.setScene(sdn); cs.initOwner(Stage); cs.show(); 
            
            //t actions
            brt.setOnAction(p->{
                FileChooser ch=new FileChooser();
                ch.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG IMAGE","*.png"), 
                        new FileChooser.ExtensionFilter("JPG IMAGE","*.jpg"),
                        new FileChooser.ExtensionFilter("ICO IMAGE","*.ico"));
                
                File hgi=ch.showOpenDialog(Stage);
                if(hgi!=null) {
                    icnf.setText(hgi.toString());
                }
            });
            
            k.setOnMousePressed(yt->{
                xoff=yt.getSceneX(); yoff=yt.getSceneY();
            });
            k.setOnMouseDragged(ty->{
                cs.setX(ty.getScreenX()-xoff); cs.setY(ty.getScreenY()-yoff);
            });
            
            kk.setOnAction(yt->{
                Properties ui=new Properties();
                ui.put("Sdecr",cbx.getValue().toString()); ui.put("Stitle",titf.getText()); ui.put("Sicon","icon.png");
                ui.put("Spcty",ctyf.getValue().toString()); ui.put("Srsz", rszf.getValue().toString()); 
                ui.put("Swidth",wf.getText()); ui.put("Sheight",hf.getText()); ui.put("minW",mwf.getText()); 
                ui.put("minH",mhf.getText()); ui.put("maxW",mxwf.getText()); ui.put("maxH",mxhf.getText());
                
                //Saving Data
                try {
                FileOutputStream in=new FileOutputStream(curr+"/AppData.dll");
                ObjectOutputStream obju=new ObjectOutputStream(in);
                obju.writeObject(ui); obju.flush(); obju.close();
                
                //writing icon File
                File fs=new File(curr+"/icon.png"); File se=new File(icnf.getText());
                if(!icnf.getText().equals("icon.png")) {
                copyFile(se, fs); }
                
                } catch (IOException ex) {
                    System.out.println("Save pr change error...");
                }
                cs.close();
                
            });
            
            apl.setOnAction(ty->{ apl.setDisable(true);
                Properties ui=new Properties();
                ui.put("Sdecr",cbx.getValue().toString()); ui.put("Stitle",titf.getText()); ui.put("Sicon","icon.png");
                ui.put("Spcty",ctyf.getValue().toString()); ui.put("Srsz", rszf.getValue().toString()); 
                ui.put("Swidth",wf.getText()); ui.put("Sheight",hf.getText()); ui.put("minW",mwf.getText()); 
                ui.put("minH",mhf.getText()); ui.put("maxW",mxwf.getText()); ui.put("maxH",mxhf.getText());
                
                //Saving Data
                try {
                FileOutputStream in=new FileOutputStream(curr+"/AppData.dll");
                ObjectOutputStream obju=new ObjectOutputStream(in);
                obju.writeObject(ui); obju.flush(); obju.close();
                
                //writing icon File
                File fs=new File(curr+"/icon.png"); File se=new File(icnf.getText());
                if(!icnf.getText().equals("icon.png")) {
                copyFile(se, fs); } 
                
                } catch (IOException ex) {
                    System.out.println("Save pr change error...");
                }
            });
            
            clc.setOnAction(yt->{
                cs.close();
            });
            
            
        } else { if(cs!=null) { cs.close(); }
            Label h=new Label("No Project Is Open"); h.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            Button v=new Button("Close"); v.setPrefSize(187,31);
            v.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(27); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(h,0,0); gf.add(v,0,1);
            
            cs=new Stage(); cs.setWidth(274); cs.setHeight(157); cs.initStyle(StageStyle.UNDECORATED);
            cs.setScene(new Scene(gf)); cs.initOwner(Stage); cs.show();
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                cs.setX(er.getScreenX()-xoff); cs.setY(er.getScreenY()-yoff);
            });
            v.setOnAction(tr->{
                cs.close();
            });
        }
        });
        
        svb.setOnAction(ev->{
            if(curr!=null && combo.getValue()!=null) { try {
                File hg=new File(curr+"/Scene/"+combo.getValue());
                FileWriter wrt=new FileWriter(hg);
                wrt.write(edt.getText()); wrt.flush(); wrt.close(); act.setText(combo.getValue()+" Saved"); fdd.play();
                
            } catch (IOException ex){
                System.out.println("ion Save");
            } 
                
            } else { if(csi!=null) {csi.close();}
            Label u=new Label("No Project/File Is Open"); u.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            Button v=new Button("Close"); v.setPrefSize(187,31);
            v.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(27); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(u,0,0); gf.add(v,0,1);
            
            csi=new Stage(); csi.setWidth(274); csi.setHeight(157); csi.initStyle(StageStyle.UNDECORATED);
            csi.setScene(new Scene(gf)); csi.initOwner(Stage); csi.show(); 
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                csi.setX(er.getScreenX()-xoff); csi.setY(er.getScreenY()-yoff);
            });
            
            v.setOnAction(tr->{
                csi.close();
            });
        }
        });
        
        nflb.setOnAction(ev->{
            if(curr!=null) {  if(cs!=null) {cs.close();}
            //top
            Label u=new Label("New File"); u.setStyle("-fx-font:normal 19px 'Cambria'; -fx-text-fill:azure");
            GridPane up=new GridPane(); up.setPadding(new Insets(5,5,5,5)); up.setVgap(3); up.setHgap(3);
            up.setAlignment(Pos.CENTER); up.setStyle("-fx-background-color:black"); up.add(u,0,0);
            
            //mdle
            Label fn=new Label("File Name"); fn.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            Label prf=new Label("Project"); prf.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            Label fn2=new Label(curr); fn2.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            TextField tf=new TextField(); tf.setStyle("-fx-font:normal 15px 'Cambria'");
            
            GridPane ffp=new GridPane(); ffp.setPadding(new Insets(8,8,8,8)); ffp.setVgap(14); ffp.setHgap(7);
            ffp.setAlignment(Pos.CENTER); ffp.setStyle(bg); ffp.add(fn,0,0); ffp.add(tf,1,0); ffp.add(prf,0,1); ffp.add(fn2,1,1);
            
            //btm
            Label ex=new Label(); ex.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            Button crt=new Button("Create"); Button clc=new Button("Cancel");
            crt.setStyle("-fx-background-color:black; -fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            clc.setStyle("-fx-background-color:black; -fx-font:normal 16px 'Cambria'; -fx-text-fill:azure");
            clc.setPrefSize(87,31); crt.setPrefSize(87,31);
            
            GridPane up2=new GridPane(); up2.setPadding(new Insets(5,5,5,5)); up2.setVgap(3); up2.setHgap(9);
            up2.setAlignment(Pos.CENTER_RIGHT); up2.setStyle(bg); 
            up2.add(ex,0,0); up2.add(crt,1,0); up2.add(clc,2,0);
            
            BorderPane d=new BorderPane(); d.setPadding(new Insets(0,0,0,0)); d.setTop(up); d.setCenter(ffp); 
            d.setBottom(up2);
            
            cs=new Stage(); cs.setWidth(589); cs.setHeight(347); cs.initStyle(StageStyle.UNDECORATED);
            cs.setScene(new Scene(d)); cs.initOwner(Stage); cs.show();
            
            up.setOnMousePressed(ui->{
                xoff=ui.getSceneX(); yoff=ui.getSceneY();
            });
            up.setOnMouseDragged(r->{
                cs.setX(r.getScreenX()-xoff); cs.setY(r.getScreenY()-yoff);
            });
            
            clc.setOnAction(er->{
                cs.close();
            });
            
            crt.setOnAction(eb->{   String hf="";
                //read temp
                try {
                    DataInputStream din=new DataInputStream(new FileInputStream("BuildTools/template.tmp"));
                    while (din.available()>0) {
                        hf=hf+din.readLine()+"\n";
                    }
                    
                    //checking for filename avalb
                    File g=new File(curr+"/Scene"); String [] cbc1=g.list(); List joi=new ArrayList();
                    joi.addAll(Arrays.asList(cbc1));
                    
                    if(!joi.contains(tf.getText()+".html")) {
                        ex.setText("");
                        
                        File h=new File(curr+"/Scene/"+tf.getText()+".html");
                        FileWriter wrt=new FileWriter(h);
                        wrt.write(hf); wrt.flush(); wrt.close();
                        
                        //updating cb
                        cbc1=g.list(); ObservableList<String> ob=FXCollections.observableArrayList(cbc1);
                        combo.setItems(ob); cs.close();
                        
                    } else {
                        ex.setText("Filename Already exists");
                    }
                    
                } catch (IOException eix) {
                    System.out.println("tmp rd error...");
                }
            });
            
        } else {
            if(cs!=null) { cs.close(); }
            Label h=new Label("No Project Is Open"); h.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            Button v=new Button("Close"); v.setPrefSize(187,31);
            v.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(27); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(h,0,0); gf.add(v,0,1);
            
            cs=new Stage(); cs.setWidth(274); cs.setHeight(157); cs.initStyle(StageStyle.UNDECORATED);
            cs.setScene(new Scene(gf)); cs.initOwner(Stage); cs.show();
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                cs.setX(er.getScreenX()-xoff); cs.setY(er.getScreenY()-yoff);
            });
            v.setOnAction(tr->{
                cs.close();
            });
        }
            
        });
        
        undb.setOnAction(ev->{
            edt.undo();
        });
        
        redb.setOnAction(ev->{
            edt.redo();
        });
        
        blb.setOnAction(ev->{
            if(curr!=null && combo.getValue()!=null) { try {
                File hg=new File(curr+"/Scene/"+combo.getValue());
                FileWriter wrt=new FileWriter(hg);
                wrt.write(edt.getText()); wrt.flush(); wrt.close(); act.setText(combo.getValue()+" Saved"); fdd.play();
                
            fdg=new FadeTransition(Duration.millis(2879)); fdg.setAutoReverse(false); fdg.setCycleCount(1); 
            fdg.setFromValue(1); fdg.setToValue(1); fdg.setNode(act); act.setText("Building Project");
            fdg.play();
            
            fdg.setOnFinished(er->{
                act.setText("Build Complete"); fdd.play();
            });
                
            } catch (IOException ex){
                System.out.println("ion Save");
            } 
                
            } else { if(csi!=null) {csi.close();}
            Label u=new Label("No Project/File Is Open"); u.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            Button v=new Button("Close"); v.setPrefSize(187,31);
            v.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(27); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(u,0,0); gf.add(v,0,1);
            
            csi=new Stage(); csi.setWidth(274); csi.setHeight(157); csi.initStyle(StageStyle.UNDECORATED);
            csi.setScene(new Scene(gf)); csi.initOwner(Stage); csi.show(); 
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                csi.setX(er.getScreenX()-xoff); csi.setY(er.getScreenY()-yoff);
            });
            
            v.setOnAction(tr->{
                csi.close();
            });
        }
        });
        
        cbbb.setOnAction(ev->{
            if(curr!=null && combo.getValue()!=null) { try {
                File hg=new File(curr+"/Scene/"+combo.getValue());
                FileWriter wrt=new FileWriter(hg);
                wrt.write(edt.getText()); wrt.flush(); wrt.close(); act.setText(combo.getValue()+" Saved"); fdd.play();
                
            fdg=new FadeTransition(Duration.millis(1978)); fdg.setAutoReverse(false); fdg.setCycleCount(1); 
            fdg.setFromValue(1); fdg.setToValue(1); fdg.setNode(act); act.setText("Cleaning Project");
            fdg.play();
            
            fdg2=new FadeTransition(Duration.millis(2879)); fdg2.setAutoReverse(false); fdg2.setCycleCount(1); 
            fdg2.setFromValue(1); fdg2.setToValue(1); fdg2.setNode(act);
            
            fdg.setOnFinished(er->{
                act.setText("Building Project"); fdg2.play();
            });
            
            fdg2.setOnFinished(yr->{
                act.setText("Build Complete"); fdd.play();
            });
                
            } catch (IOException ex){
                System.out.println("ion Save");
            } 
                
            } else { if(csi!=null) {csi.close();}
            Label u=new Label("No Project/File Is Open"); u.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            Button v=new Button("Close"); v.setPrefSize(187,31);
            v.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(27); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(u,0,0); gf.add(v,0,1);
            
            csi=new Stage(); csi.setWidth(274); csi.setHeight(157); csi.initStyle(StageStyle.UNDECORATED);
            csi.setScene(new Scene(gf)); csi.initOwner(Stage); csi.show(); 
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                csi.setX(er.getScreenX()-xoff); csi.setY(er.getScreenY()-yoff);
            });
            
            v.setOnAction(tr->{
                csi.close();
            });
        }
        });
        
        rnb.setOnAction(ev->{
            if(curr!=null && combo.getValue()!=null) { try {
                File hg=new File(curr+"/Scene/"+combo.getValue());
                FileWriter wrt=new FileWriter(hg);
                wrt.write(edt.getText()); wrt.flush(); wrt.close(); act.setText(combo.getValue()+" Saved"); fdd.play();
                
            fdg=new FadeTransition(Duration.millis(2879)); fdg.setAutoReverse(false); fdg.setCycleCount(1); 
            fdg.setFromValue(1); fdg.setToValue(1); fdg.setNode(act); act.setText("Running Project");
            fdg.play();
            
            fdg.setOnFinished(er->{
                act.setText(""); fdd.play();
            });
            
            File d=new File(curr+"/"+new File(curr).getName()+".exe"); //System.out.println(d);
            Desktop dsk=Desktop.getDesktop(); dsk.open(d); 
                
            } catch (IOException ex){
                System.out.println("ion Save");
            } 
                
            } else { if(csi!=null) {csi.close();}
            Label u=new Label("No Project/File Is Open"); u.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure");
            Button v=new Button("Close"); v.setPrefSize(187,31);
            v.setStyle("-fx-font:normal 16px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
            
            GridPane gf=new GridPane(); gf.setPadding(new Insets(5,5,5,5)); gf.setVgap(27); gf.setHgap(8);
            gf.setAlignment(Pos.CENTER); gf.setStyle(bg); gf.add(u,0,0); gf.add(v,0,1);
            
            csi=new Stage(); csi.setWidth(274); csi.setHeight(157); csi.initStyle(StageStyle.UNDECORATED);
            csi.setScene(new Scene(gf)); csi.initOwner(Stage); csi.show(); 
            
            gf.setOnMousePressed(er->{
                xoff=er.getSceneX(); yoff=er.getSceneY();
            });
            gf.setOnMouseDragged(er->{
                csi.setX(er.getScreenX()-xoff); csi.setY(er.getScreenY()-yoff);
            });
            
            v.setOnAction(tr->{
                csi.close();
            });
        }
        });
        
        //giving text editor some inteligence
        edt.setOnKeyTyped(ev->{
            gl=ev.getCharacter();
        });
        
        edt.setOnKeyReleased(ev->{  int a=edt.getCaretPosition();
            if(gl.equals("{")) {
                edt.insertText(a, "}"); gl=null;
            }
            if(gl.equals("(")) {
                edt.insertText(a, ")"); gl=null;
            }
            if(gl.equals("\"")) {
                edt.insertText(a, "\""); gl=null;
            }
            if(gl.equals("=")) {
                edt.insertText(a, "\"\""); gl=null;
            }
            if(gl.equals("/")) {
                edt.insertText(a, ">"); gl=null;
            }
        });
        
    }
    
    public static long getFileSize(URL url){
        HttpURLConnection conn=null;
        try {
            conn=(HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            return conn.getContentLengthLong();
        } catch (IOException e ){
            throw new RuntimeException(e);
        } finally {
            if(conn!=null){
                conn.disconnect();
            }
        }
    }
    
    public void copyFile(File scr, File dtn) throws IOException {
        
        FileInputStream in=null; FileOutputStream out=null;
        
        try {
            in=new FileInputStream(scr); out=new FileOutputStream(dtn);
            int c;
            while((c=in.read())!=-1) {
                out.write(c);
            }
            
        } finally {
            if(in!=null) {
                in.close();
            } 
            if(out!=null) {
                out.close();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
