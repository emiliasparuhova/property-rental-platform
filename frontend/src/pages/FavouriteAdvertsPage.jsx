import { useEffect, useState } from "react";
import UserService from "../services/UserService";
import FavouriteAdvertService from "../services/FavouriteAdvertService";
import Advert from "../components/Advert";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";


function FavouriteAdvertsPage(){
    const accessToken = useSelector((state) => state.auth.token);
    const navigate = useNavigate();

    const initialUserState = {
        id: 0
    }

    const [user, setUser] = useState(initialUserState)
    const [favouriteAdverts, setFavouriteAdverts] = useState([])

    const fetchUser = async () => {
        try {
          if (accessToken) {
            const response = await UserService.getUserByToken();
    
            if(response.data.role !== "TENANT"){
              navigate("/")
            }
    
            setUser(response.data);
            return response.data
          }
        } catch (error) {
          console.error('Error fetching user data:', error);
        }
      };

      const fetchFavouriteAdverts = async (userId) => {
        try{
            const response = await FavouriteAdvertService.getByUser({
                params: { userId: userId }
            });

            setFavouriteAdverts(response.data)

            console.log(userId)
            console.log(response.data)
        } catch (error) {
            console.error(error);   
          }
      }
    
      useEffect(() => {
        const fetchData = async () => {
            try {
              if (!accessToken) {
                navigate("/login");
                return;
              }
      
              const user = await fetchUser();
      
              const userId = user.id;
      
              await fetchFavouriteAdverts(userId);
            } catch (error) {
              console.error('Error fetching data:', error);
            }
          };
      
          fetchData();
      }, []);

      const formatTimestamp = (datetime) => {
        const timestamp = new Date(datetime);
        const hours = timestamp.getHours();
        const minutes = timestamp.getMinutes();

        const formattedTimestamp = `${hours < 10 ? '0' : ''}${hours}:${minutes < 10 ? '0' : ''}${minutes} ${timestamp.toLocaleDateString()}`;

        return formattedTimestamp
    }

      return (
        <div className="container mt-5 vh-100">
            {favouriteAdverts && favouriteAdverts.length > 0 ? (
                <div>
                    <h4>Favourite Adverts</h4>
                    {favouriteAdverts.map((favouriteAdvert) => (
                        <div className='mt-5' key={favouriteAdvert.advert.id}>
                            <Advert advert={favouriteAdvert.advert} />
                            <p className="fst-italic fw-bold small">Added to favourites at {formatTimestamp(favouriteAdvert.timestamp)}</p>
                        </div>
                    ))}
                </div>
            ) : (
                <h4>No Favourite Adverts</h4>
            )}
        </div>
    );
    
      
}

export default FavouriteAdvertsPage