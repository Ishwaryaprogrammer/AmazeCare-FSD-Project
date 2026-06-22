import axios from "axios";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";


const Register = () => {

    const [username, setUsername] = useState("");
    const [fullname, setFullname] = useState("");
    const [password, setPassword] = useState("");
    const [email, setEmail] = useState("");
    const [dob, setDob] = useState("");
    const [gender, setGender] = useState("");
    const [contact, setContact] = useState("");
    const navigate = useNavigate()
    const [errMsgUsername, setErrMsgUsername] = useState("");
    const [errMsgFullname, setErrMsgFullname] = useState("");
    const [errMsgPassword, setErrMsgPassword] = useState("");
    const [errMsgEmail, setErrMsgEmail] = useState("");
    const [errMsgDob, setErrMsgDob] = useState("");
    const [errMsgGender, setErrMsgGender] = useState("");
    const [errMsgContact, setErrMsgContact] = useState("");

    const [errMsg, setErrMsg] = useState("");
    const [msg, setMsg] = useState("");
    const postApi = "http://localhost:8080/api/patient/register"

    const register = async (e) => {
        e.preventDefault()
        // console.log(username)
        // console.log(password)
        // console.log(email)
        // console.log(dob)
        // console.log(gender)
        // console.log(contact)

        const body = {
            "username": username,
            "fullname": fullname,
            "password": password,
            "email": email,
            "dob": dob,
            "gender": gender,
            "contact": contact
        }
        try {
            const postFunction = await axios.post(postApi, body)
            setMsg("Registered Successfully")
            setUsername('')
            setFullname('')
            setPassword('')
            setEmail('')
            setDob('')
            setGender('')
            setContact('')
            setErrMsg(undefined)
            setErrMsgUsername(undefined)
            setErrMsgFullname(undefined)
            setErrMsgPassword(undefined)
            setErrMsgEmail(undefined)
            setErrMsgDob(undefined)
            setErrMsgGender(undefined)
            setErrMsgContact(undefined)
            setTimeout(() => {
                navigate("/login")
            }, 2000);

        } catch (err) {
            console.log(JSON.stringify(err.response))
            setErrMsg("Registration Failed :" + (err.response?.data?.message || ""));
            setErrMsgUsername(err.response?.data?.username || undefined)
            setErrMsgFullname(err.response?.data?.fullname || undefined)
            setErrMsgPassword(err.response?.data?.password || undefined)
            setErrMsgEmail(err.response?.data?.email || undefined)
            setErrMsgDob(err.response?.data?.dob || undefined)
            setErrMsgGender(err.response?.data?.gender || undefined)
            setErrMsgContact(err.response?.data?.contact || undefined)
            setMsg(undefined);
        }
    }


    return (

        <div className="container-fluid">
            <div className="row align-items-center" style={{ minHeight: "calc(100vh - 80px)" }}>
                <div className="col-sm-1"></div>
                <div className="col-sm-10">
                    <div className="card ">

                        <div className="card-body p-4">

                            <h2 className="text-center fw-bold text-primary mb-4">
                                Patient Registration
                            </h2>
                          

                            {msg && (<div className="alert alert-primary" >

                                {msg}
                            </div>)}

                            {errMsg && (<div className="alert alert-danger" >

                                {errMsg}
                            </div>)}

                            <div className="row mt-6">

                                <div className="col-md-6">

                                    <div className="mb-3">
                                        <label className="form-label fw-semibold">
                                            Username
                                        </label>
                                        {
                                            errMsgUsername !== undefined ?
                                                <span className="ms-2" style={{ color: 'red', fontSize: '11px' }}>
                                                    {errMsgUsername}
                                                </span> : ""
                                        }

                                        <input
                                            type="text"
                                            className="form-control"
                                            placeholder="Enter username" value={username} required
                                            onChange={(e) => {
                                                setUsername(e.target.value)
                                            }}
                                        />
                                    </div>


                                    <div className="mb-3">
                                        <label className="form-label fw-semibold">
                                            Full Name
                                        </label>
                                        {
                                            errMsgFullname !== undefined ?
                                                <span className="ms-2" style={{ color: 'red', fontSize: '11px' }}>
                                                    {errMsgFullname}
                                                </span> : ""
                                        }

                                        <input
                                            type="text"
                                            className="form-control"
                                            placeholder="Enter fullname" value={fullname} required
                                            onChange={(e) => {
                                                setFullname(e.target.value)
                                            }}
                                        />
                                    </div>

                                    <div className="mb-3">
                                        <label className="form-label fw-semibold">
                                            Email
                                        </label>
                                        {
                                            errMsgEmail !== undefined ?
                                                <span className="ms-2" style={{ color: 'red', fontSize: '11px' }}>
                                                    {errMsgEmail}
                                                </span> : ""
                                        }

                                        <input
                                            type="email"
                                            className="form-control"
                                            placeholder="Enter email" value={email} required
                                            onChange={(e) => {
                                                setEmail(e.target.value)
                                            }}
                                        />
                                    </div>

                                    <div className="mb-3">
                                        <label className="form-label fw-semibold">
                                            Password
                                        </label>
                                        {
                                            errMsgPassword !== undefined ?
                                                <span className="ms-2" style={{ color: 'red', fontSize: '11px' }}>
                                                    {errMsgPassword}
                                                </span> : ""
                                        }


                                        <input
                                            type="password"
                                            className="form-control"
                                            placeholder="Enter password" value={password} required
                                            onChange={(e) => {
                                                setPassword(e.target.value)
                                            }}
                                        />
                                    </div>

                                </div>

                                <div className="col-md-6">

                                    <div className="mb-3">
                                        <label className="form-label fw-semibold">
                                            Date of Birth
                                        </label>
                                        {
                                            errMsgDob !== undefined ?
                                                <span className="ms-2" style={{ color: 'red', fontSize: '11px' }}>
                                                    {errMsgDob}
                                                </span> : ""
                                        }

                                        <input
                                            type="date"
                                            className="form-control" value={dob} required
                                            max={new Date().toISOString().split("T")[0]}
                                            onChange={(e) => {
                                                setDob(e.target.value)
                                            }}

                                        />
                                    </div>

                                    <div className="mb-3">
                                        <label className="form-label fw-semibold">
                                            Gender
                                        </label>
                                        {
                                            errMsgGender !== undefined ?
                                                <span className="ms-2" style={{ color: 'red', fontSize: '11px' }}>
                                                    {errMsgGender}
                                                </span> : ""
                                        }

                                        <select className="form-select" required
                                            onChange={(e) => {
                                                setGender(e.target.value)
                                            }}>
                                            <option>Select Gender</option>
                                            <option>Male</option>
                                            <option>Female</option>
                                            <option>Other</option>
                                        </select>
                                    </div>

                                    <div className="mb-3">
                                        <label className="form-label fw-semibold">
                                            Contact Number
                                        </label>
                                        {
                                            errMsgContact !== undefined ?
                                                <span className="ms-2" style={{ color: 'red', fontSize: '11px' }}>
                                                    {errMsgContact}
                                                </span> : ""
                                        }

                                        <input
                                            type="text"
                                            className="form-control"
                                            placeholder="Enter contact number" value={contact} required
                                            onChange={(e) => {
                                                setContact(e.target.value)
                                            }}
                                        />
                                    </div>
                                    

                                </div>

                            </div>

                            <div className="d-grid">
                                  <p className="text-muted text-center" style={{ fontSize: "14px" }}>
                                    <i className="bi bi-info-circle me-1"></i> Information provided during registration cannot be modified later except password.
                                </p>
                                
                                <button
                                    className="btn btn-primary"
                                    type="submit" onClick={(e) => { register(e) }}
                                >
                                    Register
                                </button>

                            </div>

                            <div className="text-center mt-2">

                                Already have an account?

                                <Link
                                    to="/login"
                                    className="text-primary text-decoration-none ms-2"
                                >
                                    Login
                                </Link>

                            </div>

                        </div>

                    </div>
                </div>
                <div className="col-sm-1"></div>
            </div>



        </div>

    );
};

export default Register;