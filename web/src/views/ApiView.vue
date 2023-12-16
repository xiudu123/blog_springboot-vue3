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
    components: {ContentFieldCom}
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

        // let person = {};

        // person.userId = '1';
        // person.email = "844506672@qq.com";
        // person.cardNo = '123456788912345562';
        // person.nickName = '1230';
        // person.sex = 1;
        // person.age = 10;

        // axios.post('http://127.0.0.1:3000/user/add/success', JSON.stringify(person), {
        //     headers: {
        //         'Content-Type': 'application/json',
        //     },
        // })
        // .then(resp => {
        //     console.log(111);
        //     console.log(resp.data);
        // })
        // .catch(error => {
        //     console.log(222);
        //     console.log(error);
        // })


}
</script>

<style>

</style>