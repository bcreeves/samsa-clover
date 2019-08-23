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
                <nb-title>SAMSA Inc.</nb-title>
            </nb-body>
            <nb-right />
        </nb-header>
        <nb-content :style="{flex: 1, backgroundColor: '#ddd'}">
            <view class="home-content">
                <nb-button iconRight :onPress="() => payInvoice()" :style="{alignSelf: 'flex-end'}">
                    <nb-text>Test Payment</nb-text>
                    <nb-icon name="arrow-forward" />
                </nb-button>
            </view>
        </nb-content>
    </nb-container>
</template>

<script>
import { Button, Text } from "native-base";
import store from "../store";
import PayInvoice from "../native_modules/PayInvoice";

export default {
    props: {
        navigation: {
            type: Object
        }
    },
    methods: {
        handleLetGoBtnPress: function() {
            this.navigation.navigate("DrawerOpen");
        },
        payInvoice: function(item) {
            PayInvoice.makePayment();
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

.button-container {
    background-color: #6faf98;
    align-self: center;
}

.home-content {
    flex: 1;
    justify-content: center;
    flex-direction: row;
    flex-grow: 1;
    height: 200;
}
</style>