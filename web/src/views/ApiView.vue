<template>

    <ContentFieldCom>
        <div>
            <div id="waypoint" style="height: 500px; background-color: #eee;">waypoint</div>
        </div>
    </ContentFieldCom>

</template>


<script>
// import axios from 'axios'
import ContentFieldCom from "@/components/ContentFieldCom";
export default {
    components: {ContentFieldCom},
    /*
    getElementById 获取值为null
    setup:() => {
        const waypointElement = document.getElementById('waypoint');
        if (!waypointElement) {
            console.error("Element with id 'waypoint' not found.");
            return;
        }
        // eslint-disable-next-line no-undef
        const waypoint = new Waypoint({
            element: waypointElement,
            handler: function (direction) {
                console.log('Scrolled to waypoint! ' + direction);
            }
        });

        return {
            waypoint
        };
    }
        */

    setup() {


        // const user = {
        //     username: "xiu1",
        //     id: "123456",
        // };

    //     axios.get("http://127.0.0.1:3000/test/get2", {
    //         params: user,
    //         headers: {
    //             'Content-Type': 'application/json',
    //         },
    //     })
    //         .then(resp => {
    //             console.log(111);
    //             console.log(resp);
    //         })
    //         .catch(error => {
    //             console.log(222);
    //             console.log(error);
    //         });

        // axios.post("http://127.0.0.1:3000/test/post2", user, {
        //     headers: {
        //         'Content-Type': 'application/json',
        //     },
        // })
        //     .then(resp => {
        //         console.log(111);
        //         console.log(resp);
        //     })
        //     .catch(error => {
        //         console.log(222);
        //         console.log(error);
        //     });
    }



}
</script>

<style>

</style>