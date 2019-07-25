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
                <nb-title>Invoice {{ invoice.RefNumber }}</nb-title>
            </nb-body>
            <nb-right>
                <nb-text>SAMSA Inc.</nb-text>
            </nb-right>
        </nb-header>
        <nb-grid :style="{alignItems: 'flex-start', flex: 0}">
            <nb-row :style="{flex: 0}">
                <nb-col class="col row-header">
                    <Text>Due Date</Text>
                </nb-col>
                <nb-col class="col row-header">
                    <Text>Subtotal</Text>
                </nb-col>
                <nb-col class="col row-header">
                    <Text>Balance Remaining</Text>
                </nb-col>
            </nb-row>
            <nb-row :style="{flex: 0, borderBottomWidth: 1, borderBottomColor: '#ddd'}">
                <nb-col class="col">
                    <Text>{{ invoice.DueDate}}</Text>
                </nb-col>
                <nb-col class="col">
                    <Text>${{ invoice.Subtotal }}</Text>
                </nb-col>
                <nb-col class="col">
                    <Text>${{ invoice.BalanceRemaining }}</Text>
                </nb-col>
            </nb-row>
            <nb-row :style="{flexGrow: 1}"></nb-row>
        </nb-grid>
    </nb-container>
</template>

<script>
import React from "react";
import { Text, TouchableNativeFeedback } from "react-native";

export default {
    props: {
        navigation: {
            type: Object
        }
    },
    methods: {
        
    },
    computed: {
        customers() {
            return this.$store.state.customers;
        },
        customer() {
            return this.$store.state.customer;
        },
        invoice() {
            return this.$store.state.invoice;
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