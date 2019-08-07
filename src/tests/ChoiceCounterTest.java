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
/**
 * unfortunately this has to be in the unnamed package, since we need
 * B to be in the unnamed package (so that it has a builtin type name), and as
 * of Java 1.4 you can't import unnamed package classes into named packages
 */
import gov.nasa.jpf.util.test.TestJPF;
import gov.nasa.jpf.ChoiceCounter;

import gov.nasa.jpf.vm.Verify;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class ChoiceCounterTest extends TestJPF {

  @Test
  public void testChoiceCounter() {
    if (verifyNoPropertyViolation()) {

      ChoiceCounter.addActualChoicePair(1,1);
      ChoiceCounter.addActualChoicePair(1,2);
      ChoiceCounter.addActualChoicePair(2,1);
      ChoiceCounter.addActualChoicePair(2,2);

      int m = Verify.getInt(1,2);
      int n = Verify.getInt(1,2);
      ChoiceCounter.recordChoicePair(m ,n);

      if( m == 2 && n == 2) {
        assert ChoiceCounter.checkRecordedChoices();
      }

    }

  }
}
