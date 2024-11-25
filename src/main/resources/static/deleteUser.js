async function deleteUser(modal, id) {

    let oneUser = await userFetch.findOneUser(id);
    let user = oneUser.json();

    modal.find('.modal-title').html('Delete user');

    let deleteButton = `<button  class="btn btn-danger" id="deleteButton">Delete</button>`;
    let closeButton = `<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>`

    modal.find('.modal-footer').append(deleteButton);
    modal.find('.modal-footer').append(closeButton);

    user.then(user => {

        let bodyForm = `
            <form class="form-group text-center" id="deleteUser">
               <div class="form-group">
                    <label for="id" class="col-form-label">ID</label>
                    <input type="text" class="form-control username" id="id" value="${user.id}" readonly>
               </div>
                   
               <div class="form-group">
                    <label for="email" class="col-form-label">Email</label>
                    <input type="text" class="form-control username" id="email" value="${user.email}" readonly>
               </div>

                <div class="form-group">
                    <label for="firstName" class="com-form-label">First Name</label>
                    <input type="text" class="form-control" id="firstName" value="${user.firstName}" readonly>
                </div>

                <div class="form-group">
                    <label for="lastName" class="com-form-label">Last Name</label>
                    <input type="text" class="form-control" id="lastName" value="${user.lastName}" readonly>
                </div>

                <div class="form-group">
                    <label for="age" class="com-form-label">Age</label>
                    <input type="number" class="form-control" id="age" value="${user.age}" readonly>
                    <div class="invalid-feedback">
                        Age cannot be empty
                    </div>
                </div>
                
                 <div class="form-group">
                <label for="roles" class="com-form-label">Role:</label>
                <select id="roles" class="form-control select" size="2" name="roles" style="max-height: 100px" disabled>
                <option>${user.roles.map(role => " " + role.name)}</option>
            })}</option>
                </select>
            </div>

            </form>
        `;
        modal.find('.modal-body').append(bodyForm);
    })

    $("#deleteButton").on('click', async () => {
        const response = await userFetch.deleteUser(id);

        if (response.ok) {

            await getUsers();
            modal.modal('hide');

        } else {

            let body = await response.json();

            let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="messageError">
                            ${body.info}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;

            modal.find('.modal-body').prepend(alert);
        }
    })
}
