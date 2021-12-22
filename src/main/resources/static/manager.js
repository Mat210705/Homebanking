const app = Vue.createApp({
    data() {
        return {
            clients: [],
            firstName: "",
            lastName: "",
            email: "",
            html: "",


        }
    },
    created() {
        this.loadData();
    },
    methods: {
        loadData() {
            axios.get('http://localhost:8080/clients')
                .then(response => {
                    console.log(response);
                    this.clients = response.data._embedded.clients;
                    this.html = response.data._embedded;
                })
        },
        postClient() {
            axios.post('http://localhost:8080/clients', {
                    firstName: this.firstName,
                    lastName: this.lastName,
                    email: this.email
                })
                .then(response => {
                    this.loadData();

                });
        },


    }

})
let consol = app.mount("#app")


// postClient() {
//     axios.post('http://localhost:8080/clients', {
//             firstName: this.firstName,
//             lastName: this.lastName,
//             email: this.email
//         })
//         .then(response => {
//             this.loadData();
//         });

//     console.log("enviado")

// }