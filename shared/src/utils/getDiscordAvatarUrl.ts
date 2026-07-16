/**
 * Generate the official Discord avatar URL for a user
 */
export function getDiscordAvatarUrl(
    userId: string,
    avatarHash: string | null,
    discriminator: string | null = '0'
): string {
    // Case 1 : The user has configured a custom avatar
    if (avatarHash) {
        // If the hash starts with "a_", it's an animated avatar (Nitro)
        const extension = avatarHash.startsWith('a_') ? 'gif' : 'png';
        return `https://cdn.discordapp.com/avatars/${userId}/${avatarHash}.${extension}`;
    }

    // Case 2 : No custom avatar, calculate the default Discord avatar
    let index = 0;

    if (discriminator === '0' || !discriminator) {
        // New Discord username system (calculation based on unique ID)
        // We use BigInt because the Discord ID is too large for a standard JS Number
        index = Number((BigInt(userId) >> 22n) % 6n);
    } else {
        // Old username system (e.g., Username#1234)
        index = Number.parseInt(discriminator) % 5;
    }

    return `https://cdn.discordapp.com/embed/avatars/${index}.png`;
}
