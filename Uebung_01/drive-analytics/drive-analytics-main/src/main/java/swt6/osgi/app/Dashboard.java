package swt6.osgi.app;




import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import swt6.osgi.sensor.ISensor;
import swt6.util.JavaFxUtils;
import swt6.util.SensorListenerService;
import swt6.util.TimerBean;

public class Dashboard {
	
	private Stage stage;
	private List<EventHandler<WindowEvent>> closeHandlers = new ArrayList<>();

	private TimerBean timer;
	private Slider timeSlider;
	private List<ISensor> sensors;
	private VBox widgets;
	
	private static final Logger logger = Logger.getLogger(Dashboard.class.getName());
	
	public Dashboard(){
		timer = new TimerBean();
		timer.setNoTicks(Integer.MAX_VALUE);
		timeSlider = new Slider();
		timeSlider.valueProperty().setValue(1000);
		timeSlider.minProperty().setValue(500);
		timeSlider.maxProperty().setValue(2000);
		timeSlider.valueProperty().addListener((arg0,arg1,arg2)-> timer.setTickInterval((int)timeSlider.getValue()));
		timer.addTimerListener( listener -> {
			JavaFxUtils.runLater(()-> refreshSensors());
		});
		sensors = new ArrayList<>();
		widgets = new VBox();
		widgets.getChildren().add(timeSlider);
		
	}
	
	private Node findWidgetBySensor(ISensor sensor){
		Node box =   widgets.getChildren().stream().filter(n -> {
			if(n instanceof HBox){
				HBox b = (HBox) n;
				return b.getChildren().stream().filter(x -> sensor.getSensorId().equals(x.getId())).findAny().isPresent();
			}
			return false;
		}).findFirst().orElse(null);
		
		if(box instanceof HBox){
			return ((HBox)box).getChildren().stream().filter(n-> sensor.getSensorId().equals(n.getId())).findAny().orElse(null);
		}
		return null;
		
	}
	
	private void refreshSensors(){
		sensors.forEach(sensor -> {
			Node widget = this.findWidgetBySensor(sensor);
			if( widget != null){
				this.updateWidget(sensor, widget);
			}
		});
	}
	private void updateWidget(ISensor sensor, Node widget){
		ByteBuffer buffer = ByteBuffer.wrap(sensor.getData());
		switch(sensor.getDataFormat()){
		case ABSOLUTE_VALUE_LONG:
			Long value = buffer.getLong();
			logger.info("update absolute value widget "+ widget + "to value: "+value);
			if(widget instanceof Text){
				Text t = (Text) widget;
				t.setText(""+value);
			}else{
				logger.severe("Widget kann nicht Upgedate werden, falscher Typ");
			}
			break;
		case PERCENT:
			Double val = buffer.getDouble();
			logger.info("update percentage widget "+ widget + "to value: "+ val);
			if(widget instanceof ProgressBar){
				ProgressBar pb = (ProgressBar) widget;
				pb.setProgress(val);
			}else{
				logger.severe("Widget kann nicht Upgedate werden, falscher Typ");
			}
			break;
		}
	}
	
	public void removeSensor(ISensor sf) {
		widgets.getChildren().remove(this.findWidgetBySensor(sf).getParent());
		sensors.remove(sf);		
	}

	public void addSensor(ISensor sf) {
		ByteBuffer buffer = ByteBuffer.wrap(sf.getData());
		HBox box = new HBox();
		Label label = new Label(sf.getSensorId());
		switch(sf.getDataFormat()){
		case ABSOLUTE_VALUE_LONG:
			logger.info("add sensor for absolute value");
			Text text = new Text("" + buffer.getLong());
			text.setId(sf.getSensorId());
			box.getChildren().addAll(label,text);
			widgets.getChildren().add(box);
			 sensors.add(sf);
			break;
		case PERCENT:
			logger.info("add percentage widget");
			 ProgressBar pb = new ProgressBar(buffer.getDouble());
			
			 pb.setId(sf.getSensorId());
			 box.getChildren().addAll(label, pb);
			 widgets.getChildren().add(box);
			 sensors.add(sf);
			break;
		}
		
	}
	
	public void show(){
		if(stage == null){
			stage = new Stage();
			stage.setScene(new Scene(widgets, 500,500));
			stage.setMinHeight(250);
			stage.setMinWidth(250);
			stage.setOnCloseRequest(evt -> {
				closeHandlers.forEach(h -> h.handle(evt));
		      });
		      stage.setTitle("Drive-Analytics-Dashboard");
		    }
		    stage.show();
		    timer.statrt();
	}
	
	public void close(){
		 if (stage != null) stage.close();
		 timer.stop();
	}
	
	public void addCloseHandlers(EventHandler<WindowEvent> evt){
		closeHandlers.add(evt);
	}
	public void removeCloseHandlers(EventHandler<WindowEvent> evt){
		closeHandlers.remove(evt);
	}

}
