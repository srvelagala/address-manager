package com.sap.cloud.s4hana.examples;

import com.sap.cloud.sdk.s4hana.connectivity.ErpConfigContext;
import org.slf4j.Logger;

import com.google.gson.Gson;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/businesspartners")
public class BusinessPartnerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = CloudLoggerFactory.getLogger(BusinessPartnerServlet.class);
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response )
            throws IOException {
        try {
            final List<BusinessPartner> businessPartner =
                    new DefaultBusinessPartnerService()
                            .getAllBusinessPartner()
                            .select(BusinessPartner.BUSINESS_PARTNER,
                                    BusinessPartner.FIRST_NAME,
                                    BusinessPartner.LAST_NAME)
                            .execute(new ErpConfigContext("HSS"));
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(businessPartner));
        } catch (ODataException e) {
            logger.error("method has failed", e);
        }
    }
}
