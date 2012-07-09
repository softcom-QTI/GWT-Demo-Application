package pro.softcom.archetype.service;

import javax.ejb.EJB;

import org.apache.openejb.api.LocalClient;
import org.testng.Assert;
import org.testng.annotations.Test;

import pro.softcom.archetype.AbstractTest;
import pro.softcom.archetype.util.CustomerUtil;

@LocalClient
public class CustomerServiceTest extends AbstractTest {

    @EJB
    private CustomerService customerService;

    @Test
    public void findAll() {
        customerService.saveCustomer(CustomerUtil.getCustomer());

        Assert.assertEquals(customerService.getAllCustomers().size(), 1);
    }
}
