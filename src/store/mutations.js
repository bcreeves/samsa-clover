export function SET_CUSTOMER(state, customer) {
    state.customer = customer;
}

export function SET_CUSTOMERS(state, customers) {
    state.customers = customers;
}

export function CLEAR_CUSTOMERS(state) {
    while (state.customers.length > 0) {
        state.customers.pop();
    }
}

export function SET_INVOICES(state, invoices) {
    state.invoices = invoices;
}

export function SET_INVOICE(state, invoice) {
    state.invoice = invoice;
}

export function CLEAR_INVOICES(state) {
    while (state.invoices.length > 0) {
        state.invoices.pop();
    }
}

