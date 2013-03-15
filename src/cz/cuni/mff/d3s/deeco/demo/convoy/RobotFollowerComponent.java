/*******************************************************************************
 * Copyright 2012 Charles University in Prague
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package cz.cuni.mff.d3s.deeco.demo.convoy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.cuni.mff.d3s.deeco.annotations.In;
import cz.cuni.mff.d3s.deeco.annotations.InOut;
import cz.cuni.mff.d3s.deeco.annotations.PeriodicScheduling;
import cz.cuni.mff.d3s.deeco.annotations.Process;
import cz.cuni.mff.d3s.deeco.annotations.TriggerOnChange;
import cz.cuni.mff.d3s.deeco.knowledge.Component;
import cz.cuni.mff.d3s.deeco.knowledge.OutWrapper;


public class RobotFollowerComponent extends Component {

	public Integer battery;
	public Path path;
	public String convoyRobot; // 0 if there is no robot ahead 1 otherwise
	public List<Path> crossingRobots;

	public RobotFollowerComponent() {
		this.id = "follower";
		this.battery = new Integer(100);
		this.path = new Path();
		this.path.currentPosition = new Integer(1);
		this.path.remainingPath = new ArrayList<Integer>(
				Arrays.asList(new Integer[] { new Integer(2), new Integer(3),
						new Integer(4), new Integer(5), new Integer(6),
						new Integer(7), new Integer(8), new Integer(9) }));
		this.convoyRobot = null;
		this.crossingRobots = null;
	}

	@Process
	@PeriodicScheduling(3000)
	public static void process(@InOut("path") Path path,
			@InOut("battery") OutWrapper<Integer> battery,
			@In("convoyRobot") @TriggerOnChange String convoyRobot) {
		//System.out.println("[RobotFollowerComponent.process] convoyRobot = " + convoyRobot + ", remainingPath = " + path.remainingPath);
		if (path.remainingPath.size() > 0) {
			path.currentPosition = path.remainingPath.remove(0);
			battery.value = new Integer(battery.value - 1);
			System.out.println("Follower is moving: " + path.remainingPath);
		}
	}
}
