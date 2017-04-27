package space.exploration.mars.rover.kernel;

import space.exploration.mars.rover.communication.Radio;
import space.exploration.mars.rover.environment.MarsArchitect;
import space.exploration.mars.rover.power.Battery;
import space.exploration.mars.rover.sensor.Lidar;

import java.util.Properties;

public class Rover {
	public final String ROVER_NAME = "Curiosity";

	/* Configuration */
	Properties		marsConfig		= null;
	Properties		comsConfig		= null;
	MarsArchitect	marsArchitect	= null;
	State			state			= null;

	/* Equipment Stack */
	Battery	battery	= new Battery();
	Lidar	lidar	= null;
	Radio	radio	= null;

	/* States supported */
	State	receivingState;
	State	sensingState;
	State	movingState;
	State	exploringState;
	State	transmittingState;
	State	hibernatingState;
	State	rechargingState;

	public Battery getBatter() {
		return battery;
	}

	public Rover(Properties marsConfig, Properties comsConfig) {
		this.marsConfig = marsConfig;
		this.comsConfig = comsConfig;
		this.receivingState = new ReceivingState(this);
		this.hibernatingState = new HibernatingState(this);
		this.exploringState = new ExploringState(this);
		this.movingState = new MovingState(this);
		this.rechargingState = new RechargingState(this);
		this.sensingState = new SensingState(this);
		this.transmittingState = new TransmittingState(this);

		this.marsArchitect = new MarsArchitect(marsConfig);
		marsArchitect.setUpSurface();
		state = receivingState;
		
		this.radio = new Radio(comsConfig);
	}
}
