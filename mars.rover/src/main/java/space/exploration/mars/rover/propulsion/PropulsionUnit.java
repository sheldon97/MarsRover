package space.exploration.mars.rover.propulsion;

import space.exploration.mars.rover.environment.EnvironmentUtils;
import space.exploration.mars.rover.kernel.Rover;
import space.exploration.mars.rover.navigation.NavCell;
import space.exploration.mars.rover.navigation.NavUtil;
import space.exploration.mars.rover.navigation.NavigationEngine;

import java.awt.*;

/**
 * Created by sanket on 5/28/17.
 */
public class PropulsionUnit {
    private Rover                 rover                   = null;
    private int                   powerConsumptionPerUnit = 0;
    private Point                 source                  = null;
    private Point                 destination             = null;
    private java.util.List<Point> trajectory              = null;
    private boolean               trajectoryValid         = false;

    public PropulsionUnit(Rover rover, Point source, Point destination) {
        this.rover = rover;
        this.source = source;
        this.destination = destination;
        powerConsumptionPerUnit = Integer.parseInt(rover.getMarsConfig().getProperty(EnvironmentUtils
                                                                                             .PROPULSION_POWER_CONSUMPTION));
        requestPropulsion();
    }

    public int getPowerConsumptionPerUnit() {
        return powerConsumptionPerUnit;
    }

    public java.util.List<Point> getTrajectory() {
        return trajectory;
    }

    public boolean isTrajectoryValid() {
        return trajectoryValid;
    }

    private void requestPropulsion() {

        NavigationEngine navigationEngine = rover.getNavigationEngine();
        NavCell start = navigationEngine.getGridMap().get(NavUtil.findNavId(navigationEngine
                                                                                    .getGridMap()
                , source));
        NavCell dest = navigationEngine.getGridMap().get(NavUtil.findNavId(navigationEngine
                                                                                   .getGridMap()
                , destination));
        trajectory = navigationEngine.navigate(start, dest);
        int trajectoryLength = trajectory.size();

        trajectoryValid = (trajectoryLength > 1) ? (trajectory.get(trajectoryLength - 1).equals(destination)) :
                trajectory.get(0).equals(destination);
    }
}
