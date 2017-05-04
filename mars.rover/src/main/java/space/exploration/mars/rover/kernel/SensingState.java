/**
 * 
 */
package space.exploration.mars.rover.kernel;

import space.exploration.mars.rover.communication.RoverPingOuterClass.RoverPing;
import space.exploration.mars.rover.communication.RoverStatusOuterClass.RoverStatus;
import space.exploration.mars.rover.communication.RoverStatusOuterClass.RoverStatus.Location;
import space.exploration.mars.rover.environment.MarsArchitect;
import space.exploration.mars.rover.environment.WallBuilder;
import space.exploration.mars.rover.robot.RobotPositionsOuterClass.RobotPositions;
import space.exploration.mars.rover.sensor.Lidar;

/**
 * @author sanketkorgaonkar
 *
 */
public class SensingState implements State {

	private Rover rover = null;

	public SensingState(Rover rover) {
		this.rover = rover;
	}

	public void receiveMessage(byte[] message) {
		// TODO Auto-generated method stub

	}

	public void transmitMessage(byte[] message) {
		// TODO Auto-generated method stub

	}

	public void exploreArea() {
		// TODO Auto-generated method stub

	}

	public void performExperiments() {
		// TODO Auto-generated method stub

	}

	public void move(RobotPositions positions) {
		// TODO Auto-generated method stub

	}

	public void hibernate() {
		// TODO Auto-generated method stub

	}

	public void rechargeBattery() {
		// TODO Auto-generated method stub

	}

	public void scanSurroundings() {
		MarsArchitect marsArchitect = rover.getMarsArchitect();
		Lidar lidar = new Lidar(marsArchitect.getRobot().getLocation(), marsArchitect.getCellWidth(),
				marsArchitect.getCellWidth());
		lidar.setWallBuilder(new WallBuilder(rover.getMarsConfig()));
		lidar.scanArea();
		marsArchitect.setLidarAnimationEngine(lidar);
		marsArchitect.getLidarAnimationEngine().activateLidar();
		marsArchitect.returnSurfaceToNormal();
		
		RoverPing.Builder lidarFeedback = RoverPing.newBuilder();
		lidarFeedback.setTimeStamp(System.currentTimeMillis());
		lidarFeedback.setMsg(lidar.getStatus());

		Location location = Location.newBuilder().setX(marsArchitect.getRobot().getLocation().x)
				.setY(marsArchitect.getRobot().getLocation().y).build();

		RoverStatus.Builder rBuilder = RoverStatus.newBuilder();
		RoverStatus status = rBuilder.setBatteryLevel(rover.getBatter().getPrimaryPowerUnits())
				.setSolNumber(rover.getSol()).setLocation(location).setNotes("Lidar exercised here.")
				.setModuleMessage(lidarFeedback.build().toByteString()).setScet(System.currentTimeMillis())
				.setModuleReporting(ModuleDirectory.Module.SENSOR_LIDAR.getValue()).build();

		rover.state = rover.transmittingState;
		rover.transmitMessage(status.toByteArray());
	}

	public void performDiagnostics() {
		// TODO Auto-generated method stub

	}

}
