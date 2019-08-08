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
package gov.nasa.jpf.listener;

import gov.nasa.jpf.Config;
import gov.nasa.jpf.ListenerAdapter;
import gov.nasa.jpf.annotation.JPFOption;
import gov.nasa.jpf.search.Search;
import gov.nasa.jpf.vm.ChoiceGenerator;
import gov.nasa.jpf.vm.ClassInfo;
import gov.nasa.jpf.vm.ElementInfo;
import gov.nasa.jpf.vm.Instruction;
import gov.nasa.jpf.vm.VM;
import gov.nasa.jpf.vm.MethodInfo;
import gov.nasa.jpf.vm.ThreadInfo;
import gov.nasa.jpf.util.ChoiceCounter;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Listener tool to monitor JPF execution. This class can be used as a drop-in replacement for JPF, which is called by
 * ExecTracker. ExecTracker is mostly a VMListener of 'instructionExecuted' and a SearchListener of 'stateAdvanced' and
 * 'statehBacktracked'
 * 
 * NOTE - the ExecTracker is machine type agnostic
 */

public class ChoiceListener extends ListenerAdapter {
  
  @JPFOption(type = "Boolean", key = "et.print_insn", defaultValue = "true", comment = "print executed bytecode instructions") 
  boolean printInsn = true;
  
  @JPFOption(type = "Boolean", key = "et.print_src", defaultValue = "false", comment = "print source lines")
  boolean printSrc = false;
  
  @JPFOption(type = "Boolean", key = "et.print_mth", defaultValue = "false", comment = "print executed method names")
  boolean printMth = false;
  
  @JPFOption(type = "Boolean", key = "et.skip_init", defaultValue = "true", comment = "do not log execution before entering main()")
  boolean skipInit = false;
  
  boolean showShared = false;
  
  PrintWriter out;

  boolean skip;
  MethodInfo miMain; // just to make init skipping more efficient
  
  public ChoiceListener (Config config) {
    /** @jpfoption et.print_insn : boolean - print executed bytecode instructions (default=true). */
    printInsn = config.getBoolean("et.print_insn", true);

    /** @jpfoption et.print_src : boolean - print source lines (default=false). */
    printSrc = config.getBoolean("et.print_src", false);

    /** @jpfoption et.print_mth : boolean - print executed method names (default=false). */
    printMth = config.getBoolean("et.print_mth", false);

    /** @jpfoption et.skip_init : boolean - do not log execution before entering main() (default=true). */
    skipInit = config.getBoolean("et.skip_init", true);
    
    showShared = config.getBoolean("et.show_shared", true);
    
    if (skipInit) {
      skip = true;
    }
    
    out = new PrintWriter(System.out, true);
  }
  
  /******************************************* SearchListener interface *****/

  @Override
  public void searchFinished(Search search) {
    out.println("----------------------------------- search finished");
    assert ChoiceCounter.checkRecordedChoices(); //Checks if all the choices are made as expected
  }

}

