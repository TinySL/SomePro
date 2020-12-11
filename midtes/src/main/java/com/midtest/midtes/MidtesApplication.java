package com.midtest.midtes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;



@SpringBootApplication
public class MidtesApplication {

    public static void main(String[] args) throws InterruptedException {

        //SpringApplication.run(MidtesApplication.class, args);
        ApplicationContext applicationContext = SpringApplication.run(MidtesApplication.class, args);
        applicationContext.getBean(NIOServer.class).NIOServerR();
        //String sds = "303437323837373432ff8072e5766a80eaed2f94ec6ca0d2e02cbc6a3362d1d929629bb403d8fd3b582580c6da2e52b0813290ca13971d3fc763b01b0f285ea0385105850048dfc47663b01b0f285ea038ea4d5a8632e701760f1132b15461d322d9e4bf9c582116c2d586841c228947ac08c0eab51be4d501a7934ce33b9c979757a3df0c13a9436097766ad36129456e47f15e907fdf2bdcd67b6691fa238b516864e55122335572731175e706c139a85e4eddefab1dc429cfd02f9f106ac9fe1afeb7d764e5971c50292a52b380459121b8d21c834f1eb6957498667aab65e23c20638db99ce848d57389b6cf4f3632ae1a15c81103ca47613fdc7a12b8836eeba0edee607f5295fd00bb9e7aa32bd9914de642816c05ecc215970df27660ce54463f7c0ce671a6a33b5afc306b1c8bf676466d99f79aa7bff705d18785a23eee02066e7ef8028e8dcbe0ea784d87f29153bb789cf069dc698f5b35e02aba3c99dfaeebae35ee6aa433c498b26d262ddf5dff3323ac5f78d53ae051500a4a4aa4185763fd02ea398498bbe6b29557e89e4be8d400616696c317c9c9a5ad7714cbe8c8a25720b3d8f67993b1304449ed001870d8ddd23d3b1edcee249be7aef1c49dc71512653eda17fbaf615344bee0165b2c0d5c843fea454545";
        //String sonoutstr = "";
        //OPout outstr = new OPout();
        //String sonoutstr = outstr.newsome(sds);
        //System.out.println(sonoutstr);

        //ClentTest  clenca = new ClentTest();
        //Socket socket1 = clenca.ConnectToServerByTcp("127.0.0.1",9805,5,3);

        //sonoutstr = clenca.SockSend(socket1,sds,sonoutstr);
    }

}
