async function createUser() {
    $('#addUser').click(async () =>  {
        let addUserForm = $('#addForm')
        let firstName = addUserForm.find('#firstNameCreate').val().trim();
        let lastName = addUserForm.find('#lastNameCreate').val().trim();
        let age = addUserForm.find('#ageCreate').val().trim();
        let email = addUserForm.find('#emailCreate').val().trim();
        let password = addUserForm.find('#passwordCreate').val().trim();

        let checkedRoles = () => {
            let array = []
            let options = document.querySelector('#rolesCreate').options
            for (let i = 0; i < options.length; i++) {
                if (options[i].selected) {
                    array.push(roleList[i])
                }
            }
            return array;
        }
        let data = {
            firstName: firstName,
            lastName: lastName,
            age: age,
            email: email,
            password: password,
            roles: checkedRoles()
        }

        const response = await userFetch.addNewUser(data);

        if (response.ok) {

            await getUsers();

            addUserForm.find('#firstNameCreate').val('');
            addUserForm.find('#lastNameCreate').val('');
            addUserForm.find('#ageCreate').val('');
            addUserForm.find('#emailCreate').val('');
            addUserForm.find('#passwordCreate').val('');
            addUserForm.find(checkedRoles()).val('');

            let alert = `<div class="alert alert-success alert-dismissible fade show col-12" role="alert" id="successMessage">
                         User create successful!
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;

            addUserForm.prepend(alert);

            $('.nav-tabs a[href="#adminTable"]').tab('show');

        } else {

            let body = await response.json();
            let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="messageError">
                            ${body.info}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;

            addUserForm.prepend(alert);
        }
    });
}
