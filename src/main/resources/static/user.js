const url ='http://localhost:8080/api/userInfo'

fetch(url)
    .then(res => { res.json().then(
        user=>{
            let navBarUser = ""
            navBarUser += "<b class=\"text-white\">"+user.email+"</b>"
            navBarUser += "<span class=\"text-white\"> with roles: </span>"
            navBarUser += "<span class=\"text-white\">"
            user.roles.forEach((role) => navBarUser += role.name)
            navBarUser += "</span>"
            document.getElementById("navBarUser").innerHTML = navBarUser
            let currentUser = ""
            currentUser += "<tr>"
            currentUser += "<td>"+user.id+"</td>"
            currentUser += "<td>"+user.firstName+"</td>"
            currentUser += "<td>"+user.lastName+"</td>"
            currentUser += "<td>"+user.age+"</td>"
            currentUser += "<td>"+user.email+"</td>"
            currentUser += "<td>"
            user.roles.forEach((role) => currentUser += role.name)
            currentUser += "</td>"
            document.getElementById("userInfo").innerHTML = currentUser
            var sideBar = ""
            let allRoles = ""
            user.roles.forEach((role) => allRoles += role.name)
            sideBar += "<ul class=\"nav nav-pills flex-column\">"
            if (allRoles.includes("ADMIN")){
            sideBar += "<li class=\"nav-item\">"
            sideBar += "<a class=\"nav-link\" href=\"/admin\">Admin</a>"
            sideBar += "</li>"
            }
            sideBar += "<li class=\"nav-item\">"
            sideBar += "<a class=\"nav-link active\" th:href=\"@{/userInfo}\">User</a>"
            sideBar += "</li>"
            sideBar += "</ul>"
            document.getElementById("sideBar").innerHTML = sideBar

        }







    )
    })