package com.test.inventorymanagement.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.inventorymanagement.models.InventoryModel;
import com.test.inventorymanagement.models.ItemModel;
import com.test.inventorymanagement.services.InventoryService;
import com.test.inventorymanagement.services.ItemService;

@WebMvcTest(InventoryController.class)


public class InventoryControllerTest {
	@Autowired
    private MockMvc mockMvc;
 
	@InjectMocks
	InventoryController inventoryController;
    @MockBean
     ItemService itemService;
    @MockBean
     InventoryService inventoryService;
 
    private static ObjectMapper mapper = new ObjectMapper();
    @BeforeEach
    public void setup() {

        // this must be called for the @Mock annotations above to be processed
        // and for the mock service to be injected into the controller under
        // test.
    	System.out.println("Before Each initEach() method called");
        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(inventoryController).build();

    }
 
    @Test
    public void testGetExample() throws Exception {
        List<ItemModel> items = new ArrayList<>();
        ItemModel item = new ItemModel();
        InventoryModel inventory=new InventoryModel();
      
        item.setItemId(new Long(36));
        item.setItemName("item 36");
        item.setDescription("item 36 is super kool");
        item.setAddedOn(new Date(System.currentTimeMillis()));
        inventory.setItemId(new Long(36));
        inventory.setCount(1);
        inventory.setUpdatedOn(new Date(System.currentTimeMillis()));
        item.setInventoryByItemId(inventory);
        items.add(item);
        Mockito.when(itemService.fetchAll()).thenReturn(items);
        ResponseEntity<List<ItemModel>> data=inventoryController.showAll();
        System.out.println("GET RESPONSE"+data.getStatusCodeValue()+"****"+ data.getBody().get(0).getItemName());
      /* MvcResult requestResult= mockMvc.perform(MockMvcRequestBuilders.get("/list")).andReturn();
       System.out.println("mm"+requestResult.getResponse().getStatus()+""+requestResult.getResponse().getContentAsString());*/
       
      
    }
    
    @Test
    public void testAddEmployee() throws Exception 
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
         
       // when(employeeDAO.addEmployee(any(Employee.class))).thenReturn(true);
        ItemModel item = new ItemModel();
        InventoryModel inventory=new InventoryModel();
      
        item.setItemId(new Long(36));
        item.setItemName("item 36");
        item.setDescription("item 36 is super kool");
        item.setAddedOn(new Date(System.currentTimeMillis()));
        inventory.setItemId(new Long(36));
        inventory.setCount(1);
        inventory.setUpdatedOn(new Date(System.currentTimeMillis()));
        item.setInventoryByItemId(inventory);
        Mockito.when(itemService.save(ArgumentMatchers.any())).thenReturn(item);  
    //    String json = mapper.writeValueAsString(item);
       ResponseEntity<ItemModel> responseEntity = inventoryController.addItem(item);
       
       assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
       System.out.println(responseEntity.getBody().getDescription()+""+responseEntity.getStatusCodeValue());
      
    }

  
 
    /*@Test
    public void testPutExample() throws Exception {
        Student student = new Student();
        student.setId(2);
        student.setName("John");
        Mockito.when(studentService.updateStudent(ArgumentMatchers.any())).thenReturn(student);
        String json = mapper.writeValueAsString(student);
        mockMvc.perform(put("/putMapping").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(2)))
                .andExpect(jsonPath("$.name", Matchers.equalTo("John")));
    }*/
 
    @Test
    public void testDeleteExample() throws Exception {
    	
       // Mockito.when(itemService.deleteItem(ArgumentMatchers.anyLong()));
    	ItemModel item = new ItemModel();
    	Long id=new Long(36);
    	 Mockito.when(itemService.getItem(ArgumentMatchers.anyLong())).thenReturn(item);
    	Mockito.doNothing().when(Mockito.spy(itemService)).deleteItem(ArgumentMatchers.anyLong());
       /* MvcResult requestResult = mockMvc.perform(delete("/deleteMapping").param("itemId", "36"))
                .andExpect(status().isOk()).andExpect(status().isOk()).andReturn();*/
    	Map m=inventoryController.deleteItem(id);
    	System.out.println( m.containsKey("DeletionSuccess"));
   
    }
}
