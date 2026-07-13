export type UserType = 'USER' | 'ADMIN';

export type User = {
    discordId: string;
    username: string;
    avatar: string | null;
    discriminator: string | null;
    userType: UserType;
};
