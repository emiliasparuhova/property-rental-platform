import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import AdvertService from "../services/AdvertService";
import UserService from "../services/UserService";
import Advert from "../components/Advert";
import style from './ManageAdvertsPage.module.css'


function ManageAdvertsPage(){
    const accessToken = useSelector((state) => state.auth.token);
    const navigate = useNavigate()

    const [adverts, setAdverts] = useState([])
    const [userId, setUserId] = useState(0)

    useEffect(() => {
        const fetchData = async () => {
          if (!accessToken) {
            navigate("/login");
            return;
          }
    
          const userIdFromFetch = await fetchUser();
          if (userIdFromFetch) {
            setUserId(userIdFromFetch);
            getAdvertsByLandlord(userIdFromFetch);
          }
        };
    
        fetchData();
      }, [accessToken]);

    const fetchUser = async () => {
        try {
          if (accessToken) {
            const response = await UserService.getUserByToken();
            return response.data.id;
          }
        } catch (error) {
          console.error('Error fetching user data:', error);
        }
      };

    const getAdvertsByLandlord = async (id) => {
        const response = await AdvertService.getByLandlord(id);
        
        if (response.data){
            setAdverts(response.data)
        }
    }

    const handleNewAdvertClick = () => {
        navigate('/create-advert')
    }

    return(
        <div className={`container mt-5 ${style.manageAdvertsContainer}`}>
            <div className="row justify-content-end">
                <button onClick={handleNewAdvertClick}>Create New Advert</button>
            </div>
            <div className='row'>
                <div className='col-sm-12 col-md-11'>
                    {adverts && adverts.length > 0 ? (
                    adverts.map((advert) => (
                        <div className='mt-5' key={advert.id}>
                        <Advert advert={advert} />
                        </div>
                    ))
                    ) : (
                    <p>No adverts found</p>
                    )}
                </div>
            </div>
        </div>
    );
}


export default ManageAdvertsPage;