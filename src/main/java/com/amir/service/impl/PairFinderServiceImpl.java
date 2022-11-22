package com.amir.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.amir.service.PairFinderService;

@Component
public class PairFinderServiceImpl implements PairFinderService {

	@Override
	public Map<Integer, Set<Set<Integer>>> calcAllPossiblePairs(int[] arr) {
		HashMap<Integer, Set<Set<Integer>>> ret = new HashMap<>();
		Integer size = 1;
		while (size <= arr.length) {
			Set<Set<Integer>> set = findCollsBySize(size,ret,arr);
			ret.put(size, set);
			size++;
		}
		return ret;
	}

	private Set<Set<Integer>> findCollsBySize(Integer size, HashMap<Integer, Set<Set<Integer>>> ret, int[] arr) {
		Set<Set<Integer>> set = ret.get(size - 1);
		if (set == null) {
			set = new HashSet<>();
			for (int i = 0; i < arr.length; i++) {
				Set<Integer> set1 = new HashSet<>();
				set1.add(i);
				set.add(set1);
			}
			return set;
		}

		Set<Set<Integer>> set3 = new HashSet<>();
		for (int i = 0; i < arr.length; i++) {
			for (Set<Integer> set2 : set) {
				HashSet<Integer> eSet = new HashSet<>();
				eSet.addAll(set2);
				eSet.add(i);
				if (eSet.size() == size) {
					set3.add(eSet);
				}
			}

		}
		return set3;
	}
}
	
