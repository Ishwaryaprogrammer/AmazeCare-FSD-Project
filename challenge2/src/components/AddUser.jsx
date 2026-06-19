import axios from "axios"
import { useState } from "react"
import { useNavigate } from "react-router-dom"

const AddUser = () => {
    const [msg,setMsg]=useState("")
    const [name,setName]=useState("")
    const [email,setEmail]=useState("")
    const [phone,setPhone]=useState("")
    const [companyName,setCompanyName]=useState("")
    const navigate=useNavigate()
    

    const addFunction=async(e)=>{
        e.preventDefault()
        try{
            const body={
                name:name,
                email: email,
                phone: phone,
                companyName: companyName
            }
            
        const response=await axios.post("https://jsonplaceholder.typicode.com/users",body)
        console.log(response.data)
        setMsg("Added Successfully")

        setTimeout(() => {
            navigate(-1)
        }, 3000);


        }catch(err){
            console.log(JSON.stringify(err))
        }
    }



    return (
        <div className="container mt-3">

            <form onSubmit={(e)=>{addFunction(e)}}>
                <div className="card">
                      {msg &&
                        <div className="toast-show text-white text-center bg-success border-0 align-items-center" role="alert" aria-live="assertive" aria-atomic="true">
                            <div className="d-flex justify-content-center align-items-center text-center  ">
                                <div className="toast-body ">
                                    {msg}
                                </div>
                                <button type="button" className="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close" onClick={() => { setMsg(""); navigate(-1) }}></button>
                            </div>
                        </div>}
                    <div className="card-header fw-semibold text-center fs-1">
                        Add User
                    </div>
                    <div className="card-body">
                        <div className="mb-3 ">

                            <label className="ms-2">Name:</label>
                            <input type="text" className="form-control" placeholder="Enter Name" value={name} onChange={(e)=>{(e.target.value).trim()===""?setName(""):setName(e.target.value)}} required />
                        </div>
                        <div className="mb-3 ">

                            <label className="ms-2">Email:</label>
                            <input type="text" className="form-control" placeholder="Enter Email" value={email} onChange={(e)=>{(e.target.value).trim()===""?setEmail(""):setEmail(e.target.value)}} required />
                        </div>
                        <div className="mb-3 ">

                            <label className="ms-2">Phone:</label>
                            <input type="number" className="form-control" placeholder="Enter Phone Number" value={phone} onChange={(e)=>{(e.target.value).trim()===""?setPhone(""):setPhone(e.target.value)}} required />
                        </div>
                        <div className="mb-3 ">

                            <label className="ms-2">Company Name:</label>
                            <input type="text" className="form-control" placeholder="Enter Company name" value={companyName} onChange={(e)=>{(e.target.value).trim()===""?setCompanyName(""):setCompanyName(e.target.value)}} required />
                        </div>

                    </div>
                    <div className="card-footer text-center">
                        <button type="submit" className="btn btn-primary ">Submit</button>

                    </div>

                </div>



            </form>
        </div>
    )

}

export default AddUser