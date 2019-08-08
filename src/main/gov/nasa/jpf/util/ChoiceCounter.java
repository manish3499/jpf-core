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
package gov.nasa.jpf.util;

import java.util.HashSet;
import java.util.Set;

public class ChoiceCounter {

    private static int choiceCount = 0;
    private static Set<Integer> recordedChoiceSet = new HashSet<Integer>();
    private static Set<Integer> actualChoiceSet = new HashSet<Integer>();

    public static void countChoice () { choiceCount++; }

    public static int getChoiceCount () {
        return choiceCount;
    }

    public static void recordChoicePair(int m, int n) {
        recordedChoiceSet.add(m * 100 + n);
    }

    public static void addActualChoicePair(int m, int n) {
        actualChoiceSet.add(m * 100 + n);
    }

    public static boolean checkRecordedChoices() {
        System.out.println("Recorded choices " + recordedChoiceSet.toString());
        System.out.println("Expected choices " + actualChoiceSet.toString());
        return actualChoiceSet.equals(recordedChoiceSet);
    }


}