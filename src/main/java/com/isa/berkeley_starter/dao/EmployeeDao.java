package com.isa.berkeley_starter.dao;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.isa.berkeley_starter.factory.DatabaseFactory;
import com.isa.berkeley_starter.model.Employee;
import com.isa.berkeley_starter.utils.Utils;
import com.sleepycat.bind.tuple.StringBinding;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.bind.tuple.TupleOutput;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.OperationStatus;

@Component("employeeDao")
public class EmployeeDao implements EntityDao<String, Employee>{

	@Autowired
	@Qualifier("berkeleyDBFactory")
	private DatabaseFactory databaseFactory;

	private Database database;

	@PostConstruct
	public void init() {
		System.out.println("Init in " + getClass());
		database = databaseFactory.getDatabase("basic");
	}

	@PreDestroy
	public void preDestroy(){
		System.out.println("Predestroy in " + getClass());
		if(database != null){
			try {
				database.close();
			} catch (DatabaseException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Employee get(String key) {
		DatabaseEntry keyEntry = new DatabaseEntry();
		DatabaseEntry dataEntry = new DatabaseEntry();
		StringBinding.stringToEntry(key, keyEntry);
		Employee result = null;
		try {
			OperationStatus status = database.get(null, keyEntry, dataEntry, null);
			if (status == OperationStatus.SUCCESS) {
				TupleInput input = TupleBinding.entryToInput(dataEntry);
				int size = input.readInt();
				byte[] serializedObj = new byte[size];
				input.read(serializedObj);
				result = (Employee)Utils.deserialize(serializedObj);
			}
		} catch (DatabaseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public void set(String key, Employee value) {
		DatabaseEntry keyEntry = new DatabaseEntry();
		DatabaseEntry dataEntry = new DatabaseEntry();
		OperationStatus status = null;
		try {
			StringBinding.stringToEntry(key, keyEntry);
			TupleOutput output = new TupleOutput();
			byte[] serializedObj = Utils.serialize(value);
			output.writeInt(serializedObj.length);
			output.write(serializedObj);
			TupleBinding.outputToEntry(output, dataEntry);
			status = database.put(null, keyEntry, dataEntry);
		} catch (DatabaseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(String key) {
		DatabaseEntry keyEntry = new DatabaseEntry();
		StringBinding.stringToEntry(key, keyEntry);
		OperationStatus status = null;
		try {
			status = database.delete(null, keyEntry);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}
}
