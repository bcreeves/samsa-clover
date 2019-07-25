// import { AsyncStorage } from 'react-native';

export function SET_CUSTOMER({ commit }, { customer }) {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            commit('CLEAR_CUSTOMERS');
            commit('SET_CUSTOMER', customer);
            resolve();
        }, 500);
    });
}

export function SET_INVOICE({ commit }, { invoice }) {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            commit('CLEAR_INVOICES');
            commit('SET_INVOICE', invoice);
            resolve();
        }, 500);
    });
}

// export function LOGIN ({ commit, state}, {userObj, navigate}) {
//   commit('LOGGING_IN', true)
//   return new Promise((resolve, reject) => {
//     setTimeout(() => {
//       commit('LOGIN_SUCCESFULL', {userObj})
//       AsyncStorage.setItem('email', userObj.email)
//       navigate('Home');
//       resolve();
//     }, 1000)
//   })
// }

// export function SET_USER({commit, state}, {userObj}) {
//   return commit('LOGIN_SUCCESFULL', {userObj})
// }

// export function LOGOUT ({ commit, state}, callback) {
//   return new Promise((resolve, reject) => {
//       AsyncStorage.removeItem('email').then(() => {
//         callback();
//         resolve();
//       })
//   })
// }
