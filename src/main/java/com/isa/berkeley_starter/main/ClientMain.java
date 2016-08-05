package com.isa.berkeley_starter.main;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.isa.berkeley_starter.dao.BasicDao;
import com.isa.berkeley_starter.dao.CounterDao;
import com.isa.berkeley_starter.dao.EmployeeDao;
import com.isa.berkeley_starter.model.Employee;

public class ClientMain {

	public static void main(String[] args) {
		
		ConfigurableApplicationContext  context = new ClassPathXmlApplicationContext("applicationContext.xml");
		CounterDao dao = (CounterDao) context.getBean("counterDao");

		dao.set("a", 1);
		dao.set("b", 55);
		dao.set("c", 100);

		System.out.println(dao.get("c"));
		System.out.println(dao.get("b"));
		System.out.println(dao.get("a"));

		dao.delete("a");
		System.out.println(dao.get("a"));

		/**
		 * Basic Dao
		 * */
		BasicDao basicDao = (BasicDao) context.getBean("basicDao");
		basicDao.set("k", "hello");
		basicDao.set("l", "world");
		basicDao.set("m", new Employee("jack", "bauer", "police"));

		System.out.println(basicDao.get("m"));
		System.out.println(basicDao.get("l"));
		System.out.println(basicDao.get("k"));

		basicDao.delete("k");
		System.out.println(basicDao.get("k"));

		/**
		 * Employee Dao
		 * */

		EmployeeDao empDao = (EmployeeDao) context.getBean("employeeDao");
		basicDao.set("1", new Employee("clark", "kent", "journalist"));
		basicDao.set("2", new Employee("behzat", "ç", "police"));
		basicDao.set("3", new Employee("jack", "bauer", "police"));

		System.out.println(basicDao.get("3"));
		System.out.println(basicDao.get("2"));
		System.out.println(basicDao.get("1"));

		basicDao.delete("3");
		System.out.println(basicDao.get("3"));
		
		context.close();
	}
}
