<template>
    <div>
        <h1>내 채팅방 목록</h1>
        <!-- 채팅방 만들기 버튼 -->
        <button @click="goToCreateRoom">채팅방 만들기</button>

        <!-- 채팅방 목록 표시 -->
        <ul>
            <li v-for="room in roomList" :key="room.roomId">
                {{ room.receiver }}
                <button @click="startChat(room.roomId, room.receiver)">채팅하기</button>
                <!-- 방 삭제하기 버튼 -->
                <button @click="deleteRoom(room.roomId)">방 삭제하기</button>
            </li>
        </ul>
    </div>
</template>

<script>
import axios from "axios";
import {getToken} from "@/authentication/token";

export default {
    name: "MyChatRoom",
    data() {
        return {
            roomList: []
        }
    },
    created() {
        // 라우터에서 전달받은 토큰 추출
        const token = getToken()
        // 토큰을 사용하여 API 요청 보내기
        axios.get(`/api/v1/chat/rooms`, {
            headers: {
                Authorization: `Bearer ${token}`, // 토큰을 Authorization 헤더에 추가
            },
        })
            .then((response) => {
                // API 응답 처리
                this.roomList = response.data;
                console.log('내 채팅방 목록:', response.data);
            })
            .catch((error) => {
                console.error('API 요청 실패', error);
            });
    },
    methods: {
        goToCreateRoom() {
            this.$router.push({name: 'CreateChatRoom'});
        },
        startChat(roomId, receiver) {
            this.$router.push({name: 'ChatWithUser', params: {roomId}});
            localStorage.setItem('roomId', roomId);
            localStorage.setItem('receiver', receiver);
        },
        async deleteRoom(roomId) {
            if (roomId === undefined) {
                console.error("Invalid roomId:", roomId);
                return; // roomId가 정의되지 않은 경우 요청을 보내지 않음
            }

            const token = getToken();
            axios.delete(`/api/v1/chat/${roomId}`, {
                headers: {
                    Authorization: `Bearer ${token}`, // 토큰을 Authorization 헤더에 추가
                },
            })
                .then(() => {
                    console.log(`Chat room with ID ${roomId} deleted successfully.`);
                    location.reload();
                })
                .catch((error) => {
                    console.error("Error deleting chat room:", error);
                });
        }
    },
};
</script>