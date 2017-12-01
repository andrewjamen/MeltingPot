var statusModel;

window.addEventListener("DOMContentLoaded", function () {
    if (document.getElementById("statusPanel") !== null) {
        statusModel = new Vue({
            el: "#statusPanel",
            data: {
                edit: false,
                statusInput: ""
            },
            methods: {
                toggle: function () {
                    this.edit = !this.edit;
                },
                startEdit: function () {
                    this.edit = true;
                },
                cancelEdit: function () {
                    this.edit = false;
                    this.statusInput = "";
                }
            }
        })

        document.getElementById("editButton").addEventListener("click", function () {
            statusModel.startEdit()
        });
        document.getElementById("cancelButton").addEventListener("click", function () {
            statusModel.cancelEdit()
        });
    }
}, false);

function onStatusSubmit() {
    statusModel.cancelEdit();
}