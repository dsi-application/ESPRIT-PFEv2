export default function authHeader()
{
    // JSON.parse(sessionStorage.getItem("teacher"))
    const teacher = JSON.parse(sessionStorage.getItem('teacher'));

    if (teacher && teacher.accessToken) {
        return { Authorization: 'Bearer ' + teacher.accessToken }; // for Spring Boot back-end
        // return { 'x-access-token': user.accessToken };       // for Node.js Express back-end
    } else {
        return {};
    }

   /* const responsible = JSON.parse(sessionStorage.getItem('responsible'));
    if (responsible && responsible.accessToken)
    {
        // return { Authorization: 'Bearer ' + responsible.accessToken }; // for Spring Boot back-end
        return { 'x-access-token': responsible.accessToken };       // for Node.js Express back-end
    }
    else
    {
        return {};
    }
*/
/*
    const student = JSON.parse(sessionStorage.getItem('student'));
    if (student && student.accessToken)
    {
        // return { Authorization: 'Bearer ' + student.accessToken }; // for Spring Boot back-end
        return { 'x-access-token': student.accessToken };       // for Node.js Express back-end
    }
    else
    {
        return {};
    }
*/

}
