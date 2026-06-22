import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

const OnboardDoctor = () => {

    const navigate=useNavigate()

    const [specialtyList, setSpecialtyList] = useState([])
    const [name, setName] = useState("");
    const [fullname, setFullName] = useState("");
    const [email, setEmail] = useState("");
    const [specialty, setSpecialty] = useState("");
    const [experience, setExperience] = useState("");
    const [qualification, setQualification] = useState("");
    const [designation, setDesignation] = useState("");

    const [msg, setMsg] = useState("");
    const [errMsg, setErrMsg] = useState("");

    const [errMsgName, setErrMsgName] = useState("");
    const [errMsgFullName, setErrMsgFullName] = useState("");
    const [errMsgEmail, setErrMsgEmail] = useState("");
    const [errMsgSpecialty, setErrMsgSpecialty] = useState("");
    const [errMsgExperience, setErrMsgExperience] = useState("");
    const [errMsgQualification, setErrMsgQualification] = useState("");
    const [errMsgDesignation, setErrMsgDesignation] = useState("");

    const postApi = "http://localhost:8080/api/doctor/add";

    const addDoctor = async (e) => {

        e.preventDefault();


        if (specialty === "") {
            setErrMsgSpecialty("Please select a specialty");
            return;
        }

        const body = {
            username: name,
            fullname: fullname,
            email: email,
            specialty: specialty,
            experience: experience,
            qualification: qualification,
            designation: designation
        };

        const config = {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        }

        try {

            await axios.post(postApi, body, config);

            setMsg("Doctor Added Successfully");

            setErrMsg(undefined);
            setErrMsgName(undefined);
            setErrMsgFullName(undefined);
            setErrMsgEmail(undefined);
            setErrMsgSpecialty(undefined);
            setErrMsgExperience(undefined);
            setErrMsgQualification(undefined);
            setErrMsgDesignation(undefined);

            setName("");
            setFullName("");
            setEmail("");
            setSpecialty("");
            setExperience("");
            setQualification("");
            setDesignation("");

        } catch (err) {

            console.log(JSON.stringify(err.response));

            setErrMsg("Onboarding Failed : " + (err.response?.data?.message || ""));

            setErrMsgName(err.response?.data?.name || undefined);
            setErrMsgFullName(err.response?.data?.fullname || undefined);
            setErrMsgEmail(err.response?.data?.email || undefined);
            setErrMsgSpecialty(err.response?.data?.specialty || undefined);
            setErrMsgExperience(err.response?.data?.experience || undefined);
            setErrMsgQualification(err.response?.data?.qualification || undefined);
            setErrMsgDesignation(err.response?.data?.designation || undefined);

            setMsg(undefined);
        }
    }

    useEffect(() => {

        const getSpecialties = async () => {
            try {
                
                const response = await axios.get("http://localhost:8080/api/doctor/get-specialties")
                setSpecialtyList(response.data.specialtyList)
            } catch (err) {
                console.log(err?.response?.data)
            }
        }

        if (specialtyList.length === 0) {
            getSpecialties()
        }
    }, [])

    return (

        <div className="dashboard-content flex-grow-1 p-4" style={{ minHeight: "calc(100vh - 56px)" }}>

            <div className="row justify-content-center">

                <div className="col-lg-10">

                    <div className="card shadow-sm border-0">

                        <div className="card-body p-4">

                            <h3 className="fw-bold">
                                Add New Doctor
                            </h3>



                            {msg && (
                                <div className="alert alert-success">
                                    {msg}
                                </div>
                            )}

                            {errMsg && (
                                <div className="alert alert-danger">
                                    {errMsg}
                                </div>
                            )}

                            <form>

                                <div className="row">

                                    <div className="col-md-6">

                                        <div className="mb-4">

                                            <label className="form-label fw-semibold">
                                                Username
                                            </label>

                                            {
                                                errMsgName !== undefined ?
                                                    <span className="ms-2" style={{ color: 'red', fontSize: '11px' }}>
                                                        {errMsgName}
                                                    </span> : ""
                                            }

                                            <input type="text" className="form-control"
                                                placeholder="Enter doctor username"
                                                value={name}
                                                onChange={(e) => { setName(e.target.value) }} />

                                        </div>

                                    </div>

                                    <div className="col-md-6">

                                        <div className="mb-4">

                                            <label className="form-label fw-semibold">
                                                Experience (Years)
                                            </label>

                                            {
                                                errMsgExperience !== undefined ?
                                                    <span className="ms-2" style={{ color: 'red', fontSize: '11px' }}>
                                                        {errMsgExperience}
                                                    </span> : ""
                                            }

                                            <input type="number" className="form-control"
                                                placeholder="Enter experience"
                                                value={experience}
                                                onChange={(e) => { setExperience(e.target.value) }} />

                                        </div>

                                    </div>

                                    <div className="col-md-6">

                                        <div className="mb-4">

                                            <label className="form-label fw-semibold">
                                                Full Name
                                            </label>

                                            {
                                                errMsgFullName !== undefined ?
                                                    <span className="ms-2" style={{ color: 'red', fontSize: '11px' }}>
                                                        {errMsgFullName}
                                                    </span> : ""
                                            }

                                            <input type="text" className="form-control"
                                                placeholder="Enter doctor full name"
                                                value={fullname}
                                                onChange={(e) => { setFullName(e.target.value) }} />

                                        </div>

                                    </div>




                                    <div className="col-md-6">

                                        <div className="mb-4">

                                            <label className="form-label fw-semibold">
                                                Qualification
                                            </label>

                                            {
                                                errMsgQualification !== undefined ?
                                                    <span className="ms-2" style={{ color: 'red', fontSize: '11px' }}>
                                                        {errMsgQualification}
                                                    </span> : ""
                                            }

                                            <input type="text" className="form-control"
                                                placeholder="Enter qualification"
                                                value={qualification}
                                                onChange={(e) => { setQualification(e.target.value) }} />

                                        </div>

                                    </div>

                                    <div className="col-md-6">

                                        <div className="mb-4">

                                            <label className="form-label fw-semibold">
                                                Email
                                            </label>

                                            {
                                                errMsgEmail !== undefined ?
                                                    <span className="ms-2" style={{ color: 'red', fontSize: '11px' }}>
                                                        {errMsgEmail}
                                                    </span> : ""
                                            }

                                            <input type="email" className="form-control"
                                                placeholder="Enter email"
                                                value={email}
                                                onChange={(e) => { setEmail(e.target.value) }} />

                                        </div>

                                    </div>



                                    <div className="col-md-6">

                                        <div className="mb-4">

                                            <label className="form-label fw-semibold">
                                                Designation
                                            </label>

                                            {
                                                errMsgDesignation !== undefined ?
                                                    <span className="ms-2" style={{ color: 'red', fontSize: '11px' }}>
                                                        {errMsgDesignation}
                                                    </span> : ""
                                            }

                                            <input type="text" className="form-control"
                                                placeholder="Enter designation"
                                                value={designation}
                                                onChange={(e) => { setDesignation(e.target.value) }} />

                                        </div>

                                    </div>

                                    <div className="col-md-6">

                                        <div className="mb-4">

                                            <label className="form-label fw-semibold">
                                                Specialty
                                            </label>

                                            {
                                                errMsgSpecialty !== undefined ?
                                                    <span className="ms-2" style={{ color: 'red', fontSize: '11px' }}>
                                                        {errMsgSpecialty}
                                                    </span> : ""
                                            }

                                            <select className="form-select"
                                                value={specialty}
                                                onChange={(e) => { setSpecialty(e.target.value) }}>

                                                <option value="">Select Specialty</option>
                                                {
                                                    specialtyList.map((specialty, index) => (
                                                        <option key={index} value={specialty}>
                                                            {specialty}
                                                        </option>
                                                    ))
                                                }


                                            </select>

                                        </div>

                                    </div>

                                </div>
                                <div className="d-flex justify-content-between mt-5">

                                    <button
                                        type="button"
                                        className="btn btn-secondary px-5 ms-5"
                                        onClick={() => navigate(-1)}
                                    >
                                        <i className="bi bi-arrow-left me-2"></i>
                                        Back
                                    </button>

                                    <button type="submit" className="btn btn-primary px-4 me-5"
                                        onClick={(e) => { addDoctor(e) }}>
                                        <i className="bi bi-person-plus me-2"></i>
                                        Add Doctor
                                    </button>

                                </div>

                            </form>

                        </div>

                    </div>

                </div>

            </div>

        </div>

    );
};

export default OnboardDoctor;