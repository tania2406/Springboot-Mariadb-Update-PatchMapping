package com.example.update_example;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeRepository erepo;
	@RequestMapping("/test")
			public String test()
			{
		return "this is test for update file";
			}
	@PostMapping("/save")
	public String save(@RequestBody Employee emp)
	{
		erepo.save(emp);
		return "data saved";
	}
	@GetMapping("/all")
	public List<Employee> alldata()
	{
		return erepo.findAll();
	}
	@GetMapping("/{id}")
	public Optional<Employee> byid(@PathVariable int id)
	{
		return erepo.findById(id);
	
	}
	@GetMapping("/name/{name}")
	public List<Employee> byname(@PathVariable String name)
	{
		return erepo.findByName(name);
	}
	@GetMapping("/city/{city}")
	public List<Employee>bycity(@PathVariable String city)
	{
		return erepo.findByCity(city);
	}
//	@PutMapping("/upd/{id}")
//	public Employee update(@RequestBody Employee emp,@PathVariable int id)
//	{
//		Employee E=erepo.findById(id).get();
//		E.setAge(emp.getAge());
//		E.setCity(emp.getCity());
//		E.setName(emp.getName());
//		return erepo.save(E);
//		
//		//erepo.save
//	}
//	@PatchMapping("/upd/{id}")
//	public Employee update(@RequestBody Employee emp,@PathVariable int id)
//	{
//		Employee E=erepo.findById(id).get();
//		E.setAge(emp.getAge());
//		E.setCity(emp.getCity());
//		E.setName(emp.getName());
//		return erepo.save(E);
//	}
	@RequestMapping("/update/{id}")
	public Employee update(@PathVariable int id, @RequestBody Map<String,Object> fields)
	{
		Optional <Employee> E=erepo.findById(id);
		fields.forEach((key,value)->
		{
			Field field =ReflectionUtils.findRequiredField(Employee.class,key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, E.get(), value);
		}
		);
		return erepo.save(E.get());
		
		}
	}

