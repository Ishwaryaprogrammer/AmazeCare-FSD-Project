import axios from "axios";
import { useEffect, useState } from "react";
import { Chart } from 'primereact/chart';
const AdminGraph2 = () => {

    const apiUrl = "http://localhost:8080/api/admin/graph2"
    const [title, setTitle] = useState("")
    const [chartData, setChartData] = useState({})
    const [chartOptions, setChartOptions] = useState({})
    const [totalAppointments,setTotalAppointments]=useState(0)


    useEffect(() => {
        const config = {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }

        }

        const getGraph = async () => {
            try {
                const resp = await axios.get(apiUrl, config)
                console.log(JSON.stringify(resp))
                setTitle(resp.data.title)
                setTotalAppointments(resp.data.data.reduce((sum, num) => sum + num, 0))


                const documentStyle = getComputedStyle(document.documentElement);
                const data = {
                    labels: resp.data.label,
                    datasets: [
                        {
                            data: resp.data.data,
                            backgroundColor: [
                                documentStyle.getPropertyValue('--blue-500'),
                                documentStyle.getPropertyValue('--green-500'),
                                documentStyle.getPropertyValue('--orange-500'),
                                documentStyle.getPropertyValue('--purple-500')
                            ],
                            hoverBackgroundColor: [
                                documentStyle.getPropertyValue('--blue-400'),
                                documentStyle.getPropertyValue('--green-400'),
                                documentStyle.getPropertyValue('--orange-400'),
                                documentStyle.getPropertyValue('--purple-400')
                            ],
                        }
                    ]
                };
                const options = {
                    cutout: '60%'
                };


                setChartData(data);
                setChartOptions(options);


            } catch (err) {
                console.log(JSON.stringify(err))
            }


        }

        getGraph()






    }, [])

    return (

        <div className="col-lg-6">

            <div className="card border-0 shadow-sm">

                <div className="card-body">

                    <h5 className="fw-bold mb-3">
                        {title}
                    </h5>

                    <div className="card flex justify-content-center">
                        <Chart type="doughnut" data={chartData} options={chartOptions} className="w-full md:w-30rem" />
                        <p className="fw-bold text-center mb-3">Total Appointments: {totalAppointments}</p>
                    </div>
                    {/* <div
                                        className="d-flex justify-content-center align-items-center text-muted"
                                        style={{ height: "350px" }}
                                    >
                                        Specialty Bar Graph Here
                                    </div> */}

                </div>

            </div>

        </div>

    )
}
export default AdminGraph2

