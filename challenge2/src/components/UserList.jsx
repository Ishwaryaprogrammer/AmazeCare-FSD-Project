import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

const UserList = () => {

    const navigate = useNavigate()
    const [msg, setMsg] = useState("")
    const [users, setUsers] = useState([])
    useEffect(() => {
        const getUsers = async () => {
            try {
                const response = await axios.get("https://jsonplaceholder.typicode.com/users")
                console.log(response.data)
                setUsers(response?.data)

            } catch (err) {
                console.log(JSON.stringify.err)
            }
        }

        getUsers()

    }, [])
    

    const deleteFunction = async (id) => {
        try {
            const response = await axios.delete(`https://jsonplaceholder.typicode.com/users/${id}`)
            console.log(response)
            setMsg("Deleted Successfully")
            setUsers([...users].filter((user) => user.id !== id))

        }
        catch (err) {
            console.log(JSON.stringify(err))
        }
    }





    return (
        <div className="container mt-3">
            <div className="row mb-3">

            </div>
            <div>
                <h1 className="fw-bold text-center">Users List</h1>
                {msg &&
                    <div className="toast-show align-items-center text-white text-center bg-success border-0 align-items-center" role="alert" aria-live="assertive" aria-atomic="true">
                        <div className="d-flex  justify-content-center">
                            <div className="toast-body">
                                {msg}
                            </div>
                            <button type="button" className="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close" onClick={() => { setMsg("") }}></button>
                        </div>
                    </div>}


            </div>
            <div className="row mb-3">
                <div className="col-lg-9"></div>
                <div className="col-lg-3">
                    <button className="btn btn-primary btn-lg" onClick={() => { navigate("/add-user") }}>
                        <i className="bi bi-plus"> </i>
                        Add
                    </button>
                </div>


            </div>


            <div className="row mt-3 mb-3">
                <table className="table table-bordered table-hover ">
                    <thead className="">
                        <tr>
                            <th scope="col">S.no</th>
                            <th scope="col">Name</th>
                            <th scope="col">Email</th>
                            <th scope="col">Phone</th>
                            <th scope="col">Company Name </th>
                            <th scope="col">Action</th>

                        </tr>
                    </thead>
                    <tbody>
                        {
                            users.map((user, index) => (
                                <tr key={index}>
                                    <td>{index + 1}</td>
                                    <td>{user.name}</td>
                                    <td>{user.email}</td>
                                    <td>{user.phone}</td>
                                    <td>{user.company.name}</td>
                                    <td>{
                                        <button className="btn btn-light-border btn-lg text-danger"
                                            onClick={() => { deleteFunction(user.id) }}>
                                            <i className="bi bi-trash"></i>
                                        </button>
                                    }</td>
                                </tr>

                            ))
                        }



                    </tbody>
                </table>
            </div>
        </div>

    )
}
export default UserList;