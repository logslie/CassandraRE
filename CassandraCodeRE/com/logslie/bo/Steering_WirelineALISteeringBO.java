package com.indigital.bo;

import java.util.List;
import com.indigital.model.Steering_WirelineALISteering;
public interface Steering_WirelineALISteeringBO {

public void addElement(Steering_WirelineALISteering element);

public void deleteElement(int id);

public Steering_WirelineALISteering getElementById(String id);

public List<Steering_WirelineALISteering> getAllElement(Steering_WirelineALISteering element);

}
