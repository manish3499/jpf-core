/*
 * Copyright (C) 2014, United States Government, as represented by the
 * Administrator of the National Aeronautics and Space Administration.
 * All rights reserved.
 *
 * The Java Pathfinder core (jpf-core) platform is licensed under the
 * Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0. 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package gov.nasa.jpf.vm;

import gov.nasa.jpf.Config;
import gov.nasa.jpf.JPF;
import gov.nasa.jpf.annotation.MJI;
import gov.nasa.jpf.util.JPFLogger;
import gov.nasa.jpf.util.event.CheckEvent;
import gov.nasa.jpf.util.event.EventContext;
import gov.nasa.jpf.util.event.Event;
import gov.nasa.jpf.util.event.EventChoiceGenerator;
import gov.nasa.jpf.util.event.EventTree;
import gov.nasa.jpf.util.event.NoEvent;

import java.util.HashSet;
import java.util.Set;

/**
 * native peer for ChoiceCounter
 * Initially it is declared in such a manner that it simply counts the choices. If the native peer works perfectly,
 * it can be modified to check the uniqueness of the choices too.
 */
public class JPF_gov_nasa_jpf_ChoiceCounter extends NativePeer {

  private int choiceCount = 0;

  Set<Integer> recordedChoiceSet = new HashSet<Integer>();
  Set<Integer> actualChoiceSet = new HashSet<Integer>();

  public int getChoiceCount() {
    return choiceCount;
  }

  public void incrementChoiceCount() {
    choiceCount++;
  }

  @MJI
  public void countChoice (MJIEnv env, int objRef) {
    incrementChoiceCount();
  }

  @MJI
  public int getChoiceCount (MJIEnv env, int objRef) {
    return getChoiceCount();
  }

  @MJI
  public void recordChoicePair (MJIEnv env, int objRef, int m, int n) {
    recordedChoiceSet.add(m * 100 + n);
  }

  @MJI
  public void addActualChoicePair (MJIEnv env, int objRef, int m, int n) {
    actualChoiceSet.add(m * 100 + n);
  }

  @MJI
  public boolean checkRecordedChoices (MJIEnv env, int objRef) {
    return actualChoiceSet.equals(recordedChoiceSet);
  }


}
