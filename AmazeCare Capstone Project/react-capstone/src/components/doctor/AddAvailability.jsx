import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

const AddAvailability = () => {

    const navigate =useNavigate()
    const [day, setDay] = useState("");
    const [startTime, setStartTime] = useState("");
    const [endTime, setEndTime] = useState("");
    const [duration, setDuration] = useState("");

    const [msg, setMsg] = useState();
    const [errMsg, setErrMsg] = useState();

    const [errMsgDay, setErrMsgDay] = useState();
    const [errMsgStartTime, setErrMsgStartTime] = useState();
    const [errMsgEndTime, setErrMsgEndTime] = useState();
    const [errMsgDuration, setErrMsgDuration] = useState();
    const [isadded, setIsadded] = useState(false)

    const postApi = "http://localhost:8080/api/avail/add";

    const addAvailability = async (e) => {

    e.preventDefault();

    let valid = true;

    if (day === "") {
        setErrMsgDay("Please select a day");
        valid = false;
    } else {
        setErrMsgDay(undefined);
    }

    if (startTime === "") {
        setErrMsgStartTime("Please select start time");
        valid = false;
    } else {
        setErrMsgStartTime(undefined);
    }

    if (endTime === "") {
        setErrMsgEndTime("Please select end time");
        valid = false;
    } else {
        setErrMsgEndTime(undefined);
    }

    if (duration === "") {
        setErrMsgDuration("Please enter duration");
        valid = false;
    } else {
        setErrMsgDuration(undefined);
    }

    if (!valid) return;

    const start = new Date(`1970-01-01T${startTime}`);
    const end = new Date(`1970-01-01T${endTime}`);
    const diff = (end - start) / (1000 * 60);

    if (end <= start) {
        setErrMsgEndTime("End time must be after start time");
        return;
    }

    if (diff % Number(duration) !== 0 || Number(duration) < 1) {
        setErrMsgDuration(
            "Please enter duration such that it covers your availability slot"
        );
        return;
    }

  

        let body = {
            day: day,
            startTime: startTime,
            endTime: endTime,
            duration: duration
        };

        const config = {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        };

        try {

            await axios.post(postApi, body, config);

            setMsg("Availability Added Successfully");

            setDay("");
            setStartTime("");
            setEndTime("");
            setDuration("");

            setErrMsg(undefined);
            setErrMsgDay(undefined);
            setErrMsgStartTime(undefined);
            setErrMsgEndTime(undefined);
            setErrMsgDuration(undefined);
            setTimeout(() => {
                setMsg("");
            }, 2000);

        }
        catch (err) {

            console.log(err.response?.data);

            setErrMsg("Adding Availability Failed : " + (err.response?.data?.message || ""));

            setErrMsgDay(err.response?.data?.day || undefined);
            setErrMsgStartTime(err.response?.data?.startTime || undefined);
            setErrMsgEndTime(err.response?.data?.endTime || undefined);
            setErrMsgDuration(err.response?.data?.duration || undefined);


            setMsg(undefined);
        }
    }

    return (

        <div className="dashboard-content flex-grow-1 p-4" style={{ minHeight: "calc(100vh - 56px)" }}>

            <div className="row justify-content-center">

                <div className="col-lg-8">

                    <div className="card shadow-sm border-0">

                        <div className="card-body p-4">

                            <h3 className="fw-bold">
                                Add Availability
                            </h3>

                            {
                                msg !== undefined ?
                                    <div className="alert alert-success">
                                        {msg}
                                    </div>
                                    : ""
                            }

                            {
                                errMsg !== undefined ?
                                    <div className="alert alert-danger">
                                        {errMsg}
                                    </div>
                                    : ""
                            }

                            <form onSubmit={(e) => addAvailability(e)}>

                                <div className="mb-4">

                                    <label className="form-label fw-semibold">
                                        Day
                                    </label>

                                    {
                                        errMsgDay !== undefined ?
                                            <span style={{ color: 'red', fontSize: '11px', marginLeft: '10px' }}>
                                                {errMsgDay}
                                            </span>
                                            : ""
                                    }

                                    <select className="form-select" value={day} 
                                        onChange={(e) => { setDay(e.target.value) }} required>

                                        <option value="">
                                            Select Day
                                        </option>

                                        <option value="MONDAY">MONDAY</option>
                                        <option value="TUESDAY">TUESDAY</option>
                                        <option value="WEDNESDAY">WEDNESDAY</option>
                                        <option value="THURSDAY">THURSDAY</option>
                                        <option value="FRIDAY">FRIDAY</option>
                                        <option value="SATURDAY">SATURDAY</option>
                                        <option value="SUNDAY">SUNDAY</option>

                                    </select>

                                </div>

                                <div className="mb-4">

                                    <label className="form-label fw-semibold">
                                        Start Time
                                    </label>

                                    {
                                        errMsgStartTime !== undefined ?
                                            <span style={{ color: 'red', fontSize: '11px', marginLeft: '10px' }}>
                                                {errMsgStartTime}
                                            </span>
                                            : ""
                                    }

                                    <input type="time" className="form-control" 
                                        value={startTime}
                                        onChange={(e) => { setStartTime(e.target.value);  setErrMsgStartTime(undefined); }}  required/>

                                </div>

                                <div className="mb-4">

                                    <label className="form-label fw-semibold">
                                        End Time
                                    </label>

                                    {
                                        errMsgEndTime !== undefined ?
                                            <span style={{ color: 'red', fontSize: '11px', marginLeft: '10px' }}>
                                                {errMsgEndTime}
                                            </span>
                                            : ""
                                    }

                                    <input type="time" className="form-control"
                                        value={endTime}
                                        onChange={(e) => { setEndTime(e.target.value);  setErrMsgStartTime(undefined); }} required />

                                </div>

                                <div className="mb-4">

                                    <label className="form-label fw-semibold">
                                        Duration of a Slot (Minutes)
                                    </label>

                                    {
                                        errMsgDuration !== undefined ?
                                            <span style={{ color: 'red', fontSize: '11px', marginLeft: '10px' }}>
                                                {errMsgDuration}
                                            </span>
                                            : ""
                                    }

                                    <input type="number" className="form-control"
                                        placeholder="Enter duration in minutes"
                                        value={duration}
                                        onChange={(e) => { setDuration(e.target.value); setErrMsgStartTime(undefined); }} required />

                                </div>

                                <div className="d-flex justify-content-between">

                                    <button
                                        type="button"
                                        className="btn btn-outline-secondary px-4"
                                        onClick={() => {
                                            navigate(-1)
                                        }}
                                    >
                                        Back
                                    </button>

                                    <button type="submit" className="btn btn-primary px-5">
                                        Save Availability
                                    </button>

                                </div>


                            </form>

                        </div>

                    </div>

                </div>

            </div>

        </div>

    );
}

export default AddAvailability