package uwyo.cs.uwreg.dao;

import java.util.List;

import uwyo.cs.uwreg.dao.model.Foo;

public interface TestDAO {

	public List<Foo> getFoos();

	public abstract void deleteFoo(int a);

}