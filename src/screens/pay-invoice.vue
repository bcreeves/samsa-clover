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
                <nb-title>Pay Invoice</nb-title>
            </nb-body>
            <nb-right>
                <nb-text>SAMSA Inc.</nb-text>
            </nb-right>
        </nb-header>
        <nb-content padder>
            <view class="error" v-if="fetcherror">
                <nb-text>{{ fetcherror }}</nb-text>
            </view>
            <view :style="{ flex: 1, justifyContent: 'center', flexDirection: 'row' }">
                <view :style="{ flex: 1, width: '50%', textAlign: 'right', }">
                    <text :style="{ textAlign: 'right' }">Search By:</text>
                </view>
                <view :style="{ flex: 1, width: '50%', textAlign: 'left' }">
                    <nb-form>
                        <nb-item floatingLabel>
                            <nb-label>Customer Name</nb-label>
                            <nb-input v-model="customerName" />
                        </nb-item>
                        <nb-item floatingLabel>
                            <nb-label>Invoice Number</nb-label>
                            <nb-input />
                        </nb-item>
                    </nb-form>
                </view>
            </view>
            <view v-if="customers.length">
                <flat-list
                    :data="customers"
                    :render-item="(item) => renderList(item)"
                    :key-extractor="(item, index) => item.ListID"
                />
            </view>
        </nb-content>
    </nb-container>
</template>

<script>
import React from "react";
import { Text, TouchableNativeFeedback } from "react-native";
import Config from "react-native-config";

export default {
    props: {
        navigation: {
            type: Object
        }
    },
    data: function() {
        return {
            customerName: "",
            fetcherror: null
        };
    },
    methods: {
        searchCustomers: function() {
            fetch(
                "http://vmdev02.samsa.com/cloverapi/public/customer-by-name/" +
                    this.customerName +
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
                    this.fetcherror = '';
                    this.$store.commit("SET_CUSTOMERS", responseJson);
                })
                .catch(error => {
                    this.customers = "";
                    this.fetcherror = error;
                });
        },
        listOpenInvoices: function() {},
        renderList: function(item) {
            return (
                <TouchableNativeFeedback
                    onPress={() => this.onPressCustomer(item.item)}
                >
                    <Text>{item.item.Name}</Text>
                </TouchableNativeFeedback>
            );
        },
        onPressCustomer: function(item) {
            this.$store
                .dispatch("SET_CUSTOMER", {
                    customer: item
                })
                .then(() => {
                    this.navigation.navigate("OpenInvoicesByCustomer");
                });
        }
    },
    watch: {
        customerName: function(val) {
            this.searchCustomers();
        }
    },
    computed: {
        customers() {
            return this.$store.state.customers;
        },
        customer() {
            return this.$store.state.customer;
        }
    }
};
</script>

 
<style>
.imageContainer {
    flex: 1;
}
.text-color-primary {
    color: blue;
    font-family: Roboto;
}
/* .text-color-white {
  color: white;
} */
.button-container {
    background-color: #6faf98;
    align-self: center;
}
</style>