<template>
    <nb-container :style="{ backgroundColor: '#fff' }">
        <nb-header>
            <nb-left>
                <nb-button
                    transparent
                    :onPress="() => this.props.navigation.navigate('DrawerOpen')"
                >
                    <nb-icon name="menu" />
                </nb-button>
            </nb-left>
            <nb-body>
                <nb-title>Open Invoices for {{ customer.Name }}</nb-title>
            </nb-body>
            <nb-right>
                <nb-text>SAMSA Inc.</nb-text>
            </nb-right>
        </nb-header>
        <nb-grid :style="{alignItems: 'flex-start', flex: 0}">
            <nb-row :style="{flex: 0}">
                <nb-col class="col row-header">
                    <Text>Ref Number</Text>
                </nb-col>
                <nb-col class="col row-header">
                    <Text>Due Date</Text>
                </nb-col>
                <nb-col class="col row-header">
                    <Text>Subtotal</Text>
                </nb-col>
                <nb-col class="col row-header">
                    <Text>Balance Remaining</Text>
                </nb-col>
                <nb-col class="col row-header">
                    <Text></Text>
                </nb-col>
            </nb-row>
            <nb-row
                v-for="invoice in invoices"
                :key="invoice.qbsql_id"
                :style="{flex: 0, borderBottomWidth: 1, borderBottomColor: '#ddd'}"
            >
                <nb-col class="col">
                    <Text>{{ invoice.RefNumber }}</Text>
                </nb-col>
                <nb-col class="col">
                    <Text>{{ invoice.DueDate}}</Text>
                </nb-col>
                <nb-col class="col">
                    <Text>${{ invoice.Subtotal }}</Text>
                </nb-col>
                <nb-col class="col">
                    <Text>${{ invoice.BalanceRemaining }}</Text>
                </nb-col>
                <nb-col class="col">
                    <nb-button iconRight :onPress="() => payInvoice(invoice)">
                        <nb-text>Pay Invoice</nb-text>
                        <nb-icon name="arrow-forward" />
                    </nb-button>
                </nb-col>
            </nb-row>
            <nb-row :style="{flexGrow: 1}"></nb-row>
        </nb-grid>
    </nb-container>
</template>

<script>
import { Button, Text } from "native-base";
import PayInvoice from "../native_modules/PayInvoice";
import Config from "react-native-config";

export default {
    props: {
        navigation: {
            type: Object
        }
    },
    created: function() {
        this.getOpenInvoices();
    },
    methods: {
        getOpenInvoices: function() {
            fetch(
                "http://vmdev02.samsa.com/cloverapi/public/invoices-by-customer-id/" +
                    this.customer.ListID +
                    "?key=" +
                    Config.API_KEY,
                {
                    method: "GET",
                    headers: {
                        Accept: "application/json",
                        "Content-Type": "application/json"
                    }
                }
            )
                .then(response => response.json())
                .then(responseJson => {
                    this.$store.commit("SET_INVOICES", responseJson);
                })
                .catch(error => {
                    console.log(error);
                });
        },
        payInvoice: function(item) {
            this.$store
                .dispatch("SET_INVOICE", {
                    invoice: item
                })
                .then(() => {
                    // this.navigation.navigate("ViewInvoice");
                    PayInvoice.makePayment();
                });
        }
    },
    computed: {
        customers() {
            return this.$store.state.customers;
        },
        customer() {
            return this.$store.state.customer;
        },
        invoices() {
            return this.$store.state.invoices;
        }
    }
};
</script>

 
<style>
.col {
    padding-top: 10;
    padding-bottom: 10;
    padding-right: 20;
    padding-left: 20;
    display: flex;
    justify-content: center;
}

.row-header {
    background-color: #eee;
}
</style>