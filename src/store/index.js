import Vue from 'vue-native-core';
import Vuex from 'vuex';
import * as actions from './actions';
import * as mutations from './mutations';

Vue.use(Vuex);

const store = new Vuex.Store({
    state: {
        customers: [],
        customer: null,
        invoices: [],
        invoice: null
    },
    
    actions,
    mutations
    
});

export default store;
