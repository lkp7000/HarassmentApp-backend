package com.map.harass.service;

import com.map.harass.dto.MapDataDTO;
import com.map.harass.dto.MapKey;


public interface FilterService {

   MapDataDTO getFilterByVariousSelections(MapKey mapKey);
}
