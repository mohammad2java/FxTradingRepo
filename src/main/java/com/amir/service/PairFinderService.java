package com.amir.service;

import java.util.Map;
import java.util.Set;

public interface PairFinderService {

	Map<Integer, Set<Set<Integer>>> calcAllPossiblePairs(int[] arr);
}
