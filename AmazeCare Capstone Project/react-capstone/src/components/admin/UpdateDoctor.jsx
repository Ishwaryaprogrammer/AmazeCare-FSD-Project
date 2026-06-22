import axios from "axios";
import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";

const UpdateDoctor = () => {

    const navigate = useNavigate()
    
    const [specialtyList, setSpecialtyList] = useState([])

    const [specialty, setSpecialty] = useState("");
    const [experience, setExperience] = useState("");
    const [qualification, setQualification] = useState("");
    const [designation, setDesignation] = useState("");

    const [msg, setMsg] = useState("");
    const [errMsg, setErrMsg] = useState("");

    const [errMsgSpecialty, setErrMsgSpecialty] = useState("");
    const [errMsgExperience, setErrMsgExperience] = useState("");
    const [errMsgQualification, setErrMsgQualification] = useState("");
    const [errMsgDesignation, setErrMsgDesignation] = useState("");
    const { state } = useLocation();




    const [id, setId] = useState();
    const putApi = `http://localhost:8080/api/doctor/update/${id}`;

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
        console.log(state)
        setId(state.id)
        setSpecialty(state.specialty)
        setExperience(state.experience)
        setQualification(state.qualification)
        setDesignation(state.designation)

       
    }, [])


    const updateDoctor = async (e) => {

        e.preventDefault();


        if (specialty === "") {
            setErrMsgSpecialty("Please select a specialty");
            return;
        }

        const body = {
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

            const resp = await axios.put(putApi, body, config);
            console.log(resp)

            setMsg("Doctor Updated Successfully");

            setErrMsg(undefined);
            setErrMsgSpecialty(undefined);
            setErrMsgExperience(undefined);
            setErrMsgQualification(undefined);
            setErrMsgDesignation(undefined);

            setSpecialty("");
            setExperience("");
            setQualification("");
            setDesignation("");

            setTimeout(() => {
                navigate(-1)
            }, 3000)

        } catch (err) {

            console.log(JSON.stringify(err.response));

            setErrMsg("Updated Failed : " + (err.response?.data?.message || ""));

            setErrMsgSpecialty(err.response?.data?.specialty || undefined);
            setErrMsgExperience(err.response?.data?.experience || undefined);
            setErrMsgQualification(err.response?.data?.qualification || undefined);
            setErrMsgDesignation(err.response?.data?.designation || undefined);

            setMsg(undefined);
        }
    };

    return (

        <div className="dashboard-content flex-grow-1 p-4" style={{ minHeight: "calc(100vh - 56px)" }}>

            <div className="row justify-content-center">

                <div className="col-lg-10">

                    <div className="card shadow-sm border-0">

                        <div className="card-body p-4">

                            <h3 className="fw-bold text-center">
                                Update Doctor
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

                                <div className="mb-4">

                                    <label className="form-label fw-semibold">
                                        Specialty
                                    </label>

                                    {
                                        errMsgSpecialty !== undefined ?
                                            <span className="ms-2 text-danger" style={{ fontSize: "11px" }}>
                                                {errMsgSpecialty}
                                            </span> : ""
                                    }

                                    <select
                                        className="form-select"
                                        value={specialty}
                                        onChange={(e) => setSpecialty(e.target.value)}
                                    >

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

                                <div className="mb-4">

                                    <label className="form-label fw-semibold">
                                        Experience (Years)
                                    </label>

                                    {
                                        errMsgExperience !== undefined ?
                                            <span className="ms-2 text-danger" style={{ fontSize: "11px" }}>
                                                {errMsgExperience}
                                            </span> : ""
                                    }

                                    <input
                                        type="number"
                                        className="form-control"
                                        placeholder="Enter experience"
                                        value={experience}
                                        onChange={(e) => setExperience(e.target.value)}
                                    />

                                </div>

                                <div className="mb-4">

                                    <label className="form-label fw-semibold">
                                        Qualification
                                    </label>

                                    {
                                        errMsgQualification !== undefined ?
                                            <span className="ms-2 text-danger" style={{ fontSize: "11px" }}>
                                                {errMsgQualification}
                                            </span> : ""
                                    }

                                    <input
                                        type="text"
                                        className="form-control"
                                        placeholder="Enter qualification"
                                        value={qualification}
                                        onChange={(e) => setQualification(e.target.value)}
                                    />

                                </div>

                                <div className="mb-4">

                                    <label className="form-label fw-semibold">
                                        Designation
                                    </label>

                                    {
                                        errMsgDesignation !== undefined ?
                                            <span className="ms-2 text-danger" style={{ fontSize: "11px" }}>
                                                {errMsgDesignation}
                                            </span> : ""
                                    }

                                    <input
                                        type="text"
                                        className="form-control"
                                        placeholder="Enter designation"
                                        value={designation}
                                        onChange={(e) => setDesignation(e.target.value)}
                                    />

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

                                    <button
                                        type="submit"
                                        className="btn btn-primary px-3 me-5"
                                        onClick={(e) => updateDoctor(e)}
                                    >
                                        <i className="bi bi-pencil-square me-2"></i>
                                        Update Doctor
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

export default UpdateDoctor;