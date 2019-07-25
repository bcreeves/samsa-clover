 <template>
    <root>
        <app-navigation></app-navigation>
    </root>
</template>


<script>
import React from "react";
import { StackNavigator, DrawerNavigator } from "vue-native-router";
import { Root } from "native-base";

import HomeScreen from "./screens/home.vue";
import SideBarScreen from "./screens/sidebar.vue";

import PayInvoice from "./screens/pay-invoice.vue";
import OpenInvoicesByCustomer from "./screens/open-invoices-by-customer.vue";
import ViewInvoice from "./screens/view-invoice.vue";

const Drawer = DrawerNavigator(
    {
        Home: { screen: HomeScreen },
        PayInvoice: { screen: PayInvoice },
        OpenInvoicesByCustomer: { screen: OpenInvoicesByCustomer },
        ViewInvoice: { screen: ViewInvoice }
    },
    {
        initialRouteName: "Home",
        contentOptions: {
            activeTintColor: "#e91e63"
        },
        contentComponent: props => {
            // console.log("props inside contentComponent", props);
            return <SideBarScreen {...props} />;
        }
    }
);

const AppNavigation = StackNavigator(
    {
        Drawer: { screen: Drawer }
    },
    {
        initialRouteName: "Drawer",
        headerMode: "none"
    }
);

export default {
    components: { Root, AppNavigation }
};
</script> 
