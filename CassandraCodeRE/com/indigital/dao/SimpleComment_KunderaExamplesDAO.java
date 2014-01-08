package com.indigital.dao;

import java.util.List;
import com.indigital.model.SimpleComment_KunderaExamples;
public interface SimpleComment_KunderaExamplesDAO {

public void addElement(SimpleComment_KunderaExamples element);

public void deleteElement(int id);

public SimpleComment_KunderaExamples getElementById(String id);

public List<SimpleComment_KunderaExamples> getAllElement(SimpleComment_KunderaExamples element);

}
